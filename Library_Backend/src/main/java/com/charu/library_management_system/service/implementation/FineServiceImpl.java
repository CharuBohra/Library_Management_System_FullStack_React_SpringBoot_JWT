package com.charu.library_management_system.service.implementation;

import com.charu.library_management_system.dto.FineDTO;
import com.charu.library_management_system.dto.UserDTO;
import com.charu.library_management_system.dto.requestDTO.CreateFineRequestDTO;
import com.charu.library_management_system.dto.requestDTO.PaymentInitiateRequest;
import com.charu.library_management_system.dto.requestDTO.WaiveFineRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.dto.responseDTO.PaymentInitiateResponse;
import com.charu.library_management_system.enums.FineStatus;
import com.charu.library_management_system.enums.FineType;
import com.charu.library_management_system.enums.PaymentGateway;
import com.charu.library_management_system.enums.PaymentType;
import com.charu.library_management_system.exception.BookLoanNotFoundException;
import com.charu.library_management_system.exception.FineAlreadyPaidException;
import com.charu.library_management_system.exception.FineAlreadyWaivedException;
import com.charu.library_management_system.exception.FineNotFoundException;
import com.charu.library_management_system.mapper.FineMapper;
import com.charu.library_management_system.mapper.UserMapper;
import com.charu.library_management_system.models.BookLoan;
import com.charu.library_management_system.models.Fine;
import com.charu.library_management_system.models.User;
import com.charu.library_management_system.repository.BookLoanRepository;
import com.charu.library_management_system.repository.FineRepository;
import com.charu.library_management_system.repository.UserRepository;
import com.charu.library_management_system.service.BookLoanService;
import com.charu.library_management_system.service.FineService;
import com.charu.library_management_system.service.PaymentService;
import com.charu.library_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {

    private final FineRepository fineRepository;
    private final FineMapper fineMapper;
    private final BookLoanRepository bookLoanRepository;
    private final UserService userService;
    private final PaymentService paymentService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public FineDTO createFine(CreateFineRequestDTO createFineRequest) {
        BookLoan bookLoan = bookLoanRepository.findById(createFineRequest.getBookLoanId())
                .orElseThrow(()->new BookLoanNotFoundException("Book Loan not found for ID "+createFineRequest.getBookLoanId()));

        Fine fine = Fine.builder()
                .user(bookLoan.getUser())
                .bookLoan(bookLoan)
                .type(createFineRequest.getType())
                .amount(createFineRequest.getAmount())
                .status(FineStatus.PENDING)
                .reason(createFineRequest.getReason())
                .notes(createFineRequest.getNotes())
                .build();

        Fine savedFine = fineRepository.save(fine);

        return fineMapper.toDTO(savedFine);
    }

    @Override
    public PaymentInitiateResponse payFine(Long fineId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(()-> new FineNotFoundException("Fine Entry not found for ID "+fineId));

        if(fine.getStatus().equals(FineStatus.PAID))
        {
            throw new FineAlreadyPaidException("Fine already paid for ID "+fineId);
        }
        if(fine.getStatus().equals(FineStatus.WAIVED))
        {
            throw new FineAlreadyWaivedException("Fine already waived for Id "+fineId);
        }

        UserDTO user = userService.getCurrentUser();

        if (!fine.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException(
                    "You are not allowed to pay this fine"
            );
        }

        PaymentInitiateRequest paymentRequest = PaymentInitiateRequest.builder()
                .userId(user.getId())
                .paymentType(PaymentType.FINE)
                .paymentGateway(PaymentGateway.RAZORPAY)
                .amount(fine.getAmount())
                .description("Fine Payment for Id "+fineId)
                .fineId(fineId)
                .build();

        return paymentService.initiatePayment(paymentRequest);
    }

    @Override
    public void markFineAsPaid(Long fineId, BigDecimal amount, String transactionId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(()-> new FineNotFoundException("Fine Entry not found for ID "+fineId));

        fine.applyPayment(amount);
        fine.setTransactionId(transactionId);
        fine.setStatus(FineStatus.PAID);
        fine.setUpdatedAt(LocalDateTime.now());

        Fine saveFine = fineRepository.save(fine);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public FineDTO waiveFine(WaiveFineRequestDTO waiveFineRequest) {
        Fine fine = fineRepository.findById(waiveFineRequest.getFineId())
                .orElseThrow(()-> new FineNotFoundException("Fine Entry not found for ID "+waiveFineRequest.getFineId()));

        if(fine.getStatus().equals(FineStatus.PAID))
        {
            throw new FineAlreadyPaidException("Fine already paid for ID "+waiveFineRequest.getFineId());
        }
        if(fine.getStatus().equals(FineStatus.WAIVED))
        {
            throw new FineAlreadyWaivedException("Fine already waived for Id "+waiveFineRequest.getFineId());
        }
         UserDTO userDTO = userService.getCurrentUser();
        User currentAdmin = userMapper.toEntity(userDTO);

        fine.waive(currentAdmin, waiveFineRequest.getReason());

        Fine saveFine = fineRepository.save(fine);

        return fineMapper.toDTO(saveFine);
    }

    @Override
    public List<FineDTO> getMyFine(FineStatus status, FineType type) {
        UserDTO user = userService.getCurrentUser();

        List<Fine> fines;

        if(status!=null && type!=null)
        {
            fines = fineRepository.findByUserId(user.getId())
                    .stream()
                    .filter(f-> f.getStatus()==status && f.getType()==type)
                    .toList();
        }
        else if(status!=null)
        {
            fines = fineRepository.findByUserId(user.getId())
                    .stream()
                    .filter(f-> f.getStatus()==status)
                    .toList();
        }
        else if(type!=null)
        {
            fines = fineRepository.findByUserIdAndType(user.getId(),type);
        }
        else
        {
            fines = fineRepository.findByUserId(user.getId());
        }

        return fines.stream().map(fineMapper::toDTO).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponseDTO<FineDTO> getAllFine(FineStatus status, FineType type, Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        Page<Fine> finePage = fineRepository.findAllWithFilters(userId,status,type,pageable);

        return convertToPageResponse(finePage);
    }

    public PageResponseDTO<FineDTO> convertToPageResponse(Page<Fine> finePage)
    {
        List<FineDTO> fineDTOS = finePage.getContent()
                .stream()
                .map(fineMapper::toDTO)
                .toList();

        return PageResponseDTO.<FineDTO>builder()
                .content(fineDTOS)
                .pageNumber(finePage.getNumber())
                .pageSize(finePage.getSize())
                .totalPages(finePage.getTotalPages())
                .totalElements(finePage.getTotalElements())
                .first(finePage.isFirst())
                .last(finePage.isLast())
                .empty(finePage.isEmpty())
                .build();
    }
}
