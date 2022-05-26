package com.ourproject.portfoliotracker.data.account;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @PostMapping
    public String addAccount(@RequestBody final AccountDRO accountToAdd) {
        accountService.addAccount(accountToAdd);
        return "redirect:/login";
    }

/*    @GetMapping("/get")
    public List<AccountEntity> getAllAccounts(Authentication authentication) {
        //List<AccountEntity> accounts = accountService.getAccounts();
        if (authentication == null) {
            return null;
        } else {
            List<GrantedAuthority> grantedAuthorities = authentication
                    .getAuthorities()
                    .stream()
                    .filter(authority -> authority.getAuthority().equals("ROLE_ADMIN")).collect(Collectors.toList());
            if (grantedAuthorities.isEmpty()) {
                return null;
            }
        }
        return accountService.getAccounts();
    }*/

    @GetMapping
    public AccountDSO getAccount(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();
        AccountDSO accountDSO = accountService.getAccount(userName);

        return accountDSO;
    }

    @DeleteMapping
    public AccountEntity deleteAccount(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();

        return accountService.deleteAccount(userName);
    }

    @PutMapping("/password")
    public AccountEntity putPassword(Authentication authentication,
                                     @RequestBody String newPassword) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();

        return accountService.editPassword(userName, newPassword);
    }

    @PutMapping("/username")
    public AccountEntity putUserName(Authentication authentication,
                                     @RequestBody String newUserName) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();

        return accountService.editUserName(userName, newUserName);
    }

    @PutMapping("/email")
    public AccountEntity putEmail(Authentication authentication,
                                  @RequestBody String newEmail) {
        if (authentication == null) {
            return null;
        }
        String userName = authentication.getName();

        return accountService.editEmail(userName, newEmail);
    }

}
