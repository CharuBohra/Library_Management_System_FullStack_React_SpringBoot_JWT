package com.charu.library_management_system.controller;

import com.charu.library_management_system.dto.FineDTO;
import com.charu.library_management_system.dto.requestDTO.CreateFineRequestDTO;
import com.charu.library_management_system.dto.requestDTO.WaiveFineRequestDTO;
import com.charu.library_management_system.dto.responseDTO.PageResponseDTO;
import com.charu.library_management_system.dto.responseDTO.PaymentInitiateResponse;
import com.charu.library_management_system.enums.FineStatus;
import com.charu.library_management_system.enums.FineType;
import com.charu.library_management_system.service.FineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fines")
public class FineController {

    private final FineService fineService;

    @PostMapping
    public ResponseEntity<FineDTO> createFine(@Valid @RequestBody CreateFineRequestDTO createFineRequestDTO)
    {
        FineDTO fine = fineService.createFine(createFineRequestDTO);
        return ResponseEntity.ok(fine);
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<PaymentInitiateResponse> payFine(@PathVariable("id") Long fineId)
    {
        PaymentInitiateResponse fine = fineService.payFine(fineId);
        return ResponseEntity.ok(fine);
    }

    @PostMapping("/waive")
    public ResponseEntity<FineDTO> waiveFine(@Valid @RequestBody WaiveFineRequestDTO waiveFineRequestDTO)
    {
        FineDTO waiveFine = fineService.waiveFine(waiveFineRequestDTO);
        return ResponseEntity.ok(waiveFine);
    }

    @GetMapping("/my")
    public ResponseEntity<List<FineDTO>> getMyFines(@RequestParam(required = false)FineStatus status,
                                                   @RequestParam(required = false)FineType type)
    {
        List<FineDTO> myFines = fineService.getMyFine(status,type);
        return ResponseEntity.ok(myFines);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<FineDTO>> getAllFines(@RequestParam(required = false) Long userId,
                                                     @RequestParam(required = false) FineStatus status,
                                                     @RequestParam(required = false) FineType type,
                                                     @RequestParam(required = false,defaultValue = "0") int page,
                                                     @RequestParam(required = false,defaultValue = "10") int size)
    {
        PageResponseDTO<FineDTO> allFines = fineService.getAllFine(status,type,userId,page,size);
        return ResponseEntity.ok(allFines);
    }
}
