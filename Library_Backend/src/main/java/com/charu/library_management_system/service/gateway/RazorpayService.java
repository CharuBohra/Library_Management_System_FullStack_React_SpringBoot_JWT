package com.charu.library_management_system.service.gateway;

import com.charu.library_management_system.dto.responseDTO.PaymentLinkResponse;
import com.charu.library_management_system.enums.PaymentType;
import com.charu.library_management_system.exception.SubscriptionPlanNotFoundException;
import com.charu.library_management_system.models.Payment;
import com.charu.library_management_system.models.SubscriptionPlan;
import com.charu.library_management_system.models.User;
import com.charu.library_management_system.repository.SubscriptionPlanRepository;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RazorpayService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    @Value("${razorpay.key.id}")
    private String razorId;

    @Value(("${razorpay.key.secret}"))
    private String razorSecret;

    @Value("${razorpay.callback.base-url}")
    private String baseUrl;

    public PaymentLinkResponse createPaymentLink(User user , Payment payment)
    {
        try{
            RazorpayClient razorpayClient = new RazorpayClient(razorId,razorSecret);
            Long amountInPaisa = payment.getAmount().multiply(BigDecimal.valueOf(100)).longValueExact();

            JSONObject paymentLinkRequest = new JSONObject();

            paymentLinkRequest.put("amount", amountInPaisa);
            paymentLinkRequest.put("currency", payment.getCurrency());
            paymentLinkRequest.put("description", payment.getDescription());

            JSONObject customer = new JSONObject();
            customer.put("email",user.getEmail());
            customer.put("name",user.getFullName());
            if(user.getPhone()!=null)
            {
                customer.put("phone",user.getPhone());
            }

            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("email",true);
            notify.put("phone", user.getPhone()!=null);

            paymentLinkRequest.put("notify",notify);

            paymentLinkRequest.put("reminder_enable",true);

            String successUrl = baseUrl + "/payment_success/"+ payment.getId();

            paymentLinkRequest.put("callback_url",successUrl);
            paymentLinkRequest.put("callback_method","get");

            JSONObject notes = new JSONObject();
            notes.put("user_id",user.getId());
            notes.put("payment_id",payment.getId());
            if(payment.getPaymentType()== PaymentType.MEMBERSHIP)
            {
                notes.put("subscription_id",payment.getSubscription().getId());
                notes.put("plan", payment.getSubscription().getPlan().getPlanCode());
                notes.put("type",PaymentType.MEMBERSHIP);
            }else if(payment.getPaymentType()== PaymentType.FINE)
            {
                //notes.put("fine_id",fineId);
                notes.put("type",PaymentType.FINE);
            }
            
            paymentLinkRequest.put("notes",notes);
            
            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);
            
            String paymentUrl = paymentLink.get("short_url");
            String paymentId = paymentLink.get("id");

            return PaymentLinkResponse.builder()
                    .payment_link_url(paymentUrl)
                    .payment_link_id(paymentId)
                    .build();

        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject fetchPaymentDetails(String paymentId)
    {
        try{
            RazorpayClient razorpayClient = new RazorpayClient(razorId,razorSecret);
            com.razorpay.Payment payment = razorpayClient.payments.fetch(paymentId);
            return payment.toJson();
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isValidPayment(String paymentId)
    {
        try{
            JSONObject paymentDetails = fetchPaymentDetails(paymentId);

            String status = paymentDetails.optString("status");
            long amount = paymentDetails.optLong("amount");
            BigDecimal amountInRupees = BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100));

            JSONObject notes = paymentDetails.getJSONObject("notes");
            String paymentType = notes.optString("type");

            //check status
            if(!"captured".equalsIgnoreCase(status))
            {
                return false;
            }
            //check expected amount
            if(paymentType.equals(PaymentType.MEMBERSHIP.toString()))
            {
                String planCode = notes.optString("plan");
                SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findByPlanCode(planCode)
                        .orElseThrow(()->new SubscriptionPlanNotFoundException("Subscription Plan not found for planCode "+planCode));
                return amountInRupees.compareTo(subscriptionPlan.getPrice())==0;
            }else if(paymentType.equals(PaymentType.FINE.toString()))
            {
                String fineId = notes.optString("fine_id");
                //findById(fineId)
                //return fine.getAmount == amountInRupees;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
