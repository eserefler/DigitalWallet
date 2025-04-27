package com.es.digitalwallet;

import com.es.digitalwallet.controller.WalletController;
import com.es.digitalwallet.domain.enums.Currency;
import com.es.digitalwallet.domain.enums.OppositePartyType;
import com.es.digitalwallet.errorhandler.ErrorMessagesProvider;
import com.es.digitalwallet.model.request.ApproveTransactionRequest;
import com.es.digitalwallet.model.request.CreateWalletRequest;
import com.es.digitalwallet.model.request.DepositToWalletRequest;
import com.es.digitalwallet.model.request.WithdrawFromWalletRequest;
import com.es.digitalwallet.model.response.GetWalletTransactionsResponse;
import com.es.digitalwallet.model.response.GetWalletsResponse;
import com.es.digitalwallet.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = WalletController.class)
@WithMockUser
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @MockBean
    private ErrorMessagesProvider errorMessagesProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID customerId;
    private UUID walletId;
    private UUID transactionId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        walletId = UUID.randomUUID();
        transactionId = UUID.randomUUID();
    }

    private MockHttpServletRequestBuilder postWithCsrf(String urlTemplate, Object... uriVars) {
        return post(urlTemplate, uriVars).with(csrf());
    }

    private MockHttpServletRequestBuilder putWithCsrf(String urlTemplate, Object... uriVars) {
        return put(urlTemplate, uriVars).with(csrf());
    }

    private MockHttpServletRequestBuilder deleteWithCsrf(String urlTemplate, Object... uriVars) {
        return delete(urlTemplate, uriVars).with(csrf());
    }

    private MockHttpServletRequestBuilder getWithoutCsrf(String urlTemplate, Object... uriVars) {
        return get(urlTemplate, uriVars);
    }

    @Test
    void createWallet_shouldReturnCreated() throws Exception {
        CreateWalletRequest request = new CreateWalletRequest();
        request.setName("Test Wallet");
        request.setCurrency(Currency.TRY);
        request.setActiveForShopping(true);
        request.setActiveForWithdraw(true);

        mockMvc.perform(postWithCsrf("/wallets")
                        .header("x-customer-id", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(walletService).createWallet(eq(customerId), any(CreateWalletRequest.class));
    }

    @Test
    void getWallets_shouldReturnOk() throws Exception {
        when(walletService.getWalletsByCustomerId(customerId)).thenReturn(new GetWalletsResponse());

        mockMvc.perform(getWithoutCsrf("/wallets")
                        .header("x-customer-id", customerId))
                .andExpect(status().isOk());

        verify(walletService).getWalletsByCustomerId(customerId);
    }

    @Test
    void depositToWallet_shouldReturnOk() throws Exception {
        DepositToWalletRequest request = new DepositToWalletRequest();
        request.setAmount(1000);
        request.setOppositeParty(OppositePartyType.IBAN);
        request.setSource("Test Bank");

        mockMvc.perform(putWithCsrf("/wallets/{walletId}/deposit", walletId)
                        .header("x-customer-id", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(walletService).depositToWallet(eq(customerId), eq(walletId), any(DepositToWalletRequest.class));
    }

    @Test
    void withdrawFromWallet_shouldReturnOk() throws Exception {
        WithdrawFromWalletRequest request = new WithdrawFromWalletRequest();
        request.setAmount(100);
        request.setDestination("Some destination");
        request.setOppositeParty(OppositePartyType.IBAN);

        mockMvc.perform(putWithCsrf("/wallets/{walletId}/withdraw", walletId)
                        .header("x-customer-id", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(walletService).withdrawFromWallet(eq(customerId), eq(walletId), any(WithdrawFromWalletRequest.class));
    }

    @Test
    void getWalletTransactions_shouldReturnOk() throws Exception {
        when(walletService.getWalletTransactions(customerId, walletId)).thenReturn(new GetWalletTransactionsResponse());

        mockMvc.perform(getWithoutCsrf("/wallets/{walletId}/transactions", walletId)
                        .header("x-customer-id", customerId))
                .andExpect(status().isOk());

        verify(walletService).getWalletTransactions(customerId, walletId);
    }

    @Test
    void approveTransaction_shouldReturnOk() throws Exception {
        ApproveTransactionRequest request = new ApproveTransactionRequest();

        mockMvc.perform(putWithCsrf("/wallets/{walletId}/transactions/{transactionId}/approve", walletId, transactionId)
                        .header("x-customer-id", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(walletService).approveTransaction(eq(customerId), eq(walletId), eq(transactionId), any(ApproveTransactionRequest.class));
    }
}

