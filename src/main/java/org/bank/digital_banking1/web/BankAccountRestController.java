package org.bank.digital_banking1.web;

import lombok.AllArgsConstructor;
import org.bank.digital_banking1.dtos.BankAccountDto;
import org.bank.digital_banking1.dtos.OperationDto;
import org.bank.digital_banking1.dtos.PageHistoryDto;
import org.bank.digital_banking1.exceptions.BankAccountNotFoundException;
import org.bank.digital_banking1.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
public class BankAccountRestController {
    BankAccountService bankAccountService;

    public BankAccountRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }
    @GetMapping("/Accounts/{id}")
    public BankAccountDto getBankAccount(@PathVariable(name = "id") String AccountId) throws BankAccountNotFoundException {
return bankAccountService.getBankAccount(AccountId);
    }
    @GetMapping("/Accounts")
    public List<BankAccountDto> getBankAccounts(){
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/Accounts/{id}/operations")
    public List<OperationDto> getHistory(@PathVariable(name = "id") String Accountid){
        return bankAccountService.operationsList(Accountid);

    }
    @GetMapping("/Accounts/{id}/Pageoperations")
    public PageHistoryDto getAccountHistory(@PathVariable(name = "id") String Accountid,
                                            @RequestParam(name = "page",defaultValue = "0") int page,
                                            @RequestParam(name="size",defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(Accountid,page,size);

    }
}
