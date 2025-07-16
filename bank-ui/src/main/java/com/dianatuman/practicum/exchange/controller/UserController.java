package com.dianatuman.practicum.exchange.controller;

import com.dianatuman.practicum.exchange.service.AccountsService;
import com.dianatuman.practicum.exchange.service.CashService;
import com.dianatuman.practicum.exchange.service.TransferService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/user/{login}")
public class UserController {

    private final AccountsService accountsService;
    private final CashService cashService;
    private final TransferService transferService;

    public UserController(AccountsService accountsService, CashService cashService, TransferService transferService) {
        this.accountsService = accountsService;
        this.cashService = cashService;
        this.transferService = transferService;
    }

    @PreAuthorize("#login == principal.username")
    @PostMapping("/editPassword")
    public ModelAndView editPassword(@PathVariable("login") String login, @RequestParam("password") String password,
                                     @RequestParam("confirm_password") String confirmPassword,
                                     RedirectAttributes attributes)
            throws JsonProcessingException {
        String errors = "";
        if (password.isEmpty()) {
            errors = errors.concat("Password should not be empty.");
        } else if (password.equals(confirmPassword)) {
            String s = accountsService.editPassword(login, password);
            if (!s.isEmpty()) {
                errors = errors.concat(s);
            }
        } else {
            errors = errors.concat("Passwords do not match.");
        }
        if (!errors.isEmpty()) {
            attributes.addFlashAttribute("passwordErrors", errors);
        }
        return new ModelAndView("redirect:/main");
    }

    @PreAuthorize("#login == principal.username")
    @PostMapping("/editUserAccounts")
    public ModelAndView editUserAccounts(RedirectAttributes attributes, @PathVariable("login") String login,
                                         @RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "birthdate", required = false) LocalDate birthdate,
                                         @RequestParam(value = "account", required = false) String... account)
            throws JsonProcessingException {
        String errors = accountsService.editUserAccounts(login, name, birthdate, account);
        if (!errors.equals("OK")) {
            attributes.addFlashAttribute("userAccountsErrors", errors);
        }
        return new ModelAndView("redirect:/main");
    }

    @PreAuthorize("#login == principal.username")
    @PostMapping("/cash")
    public ModelAndView cash(RedirectAttributes attributes, @PathVariable("login") String login,
                             @RequestParam("currency") String currency,
                             @RequestParam("value") Float value,
                             @RequestParam("action") String action) throws JsonProcessingException {
        String cash = cashService.cash(login, currency, action, value);
        if (!cash.equals("OK")) {
            attributes.addFlashAttribute("cashErrors", cash);
        }
        return new ModelAndView("redirect:/main");
    }

    @PreAuthorize("#login == principal.username")
    @PostMapping("/transfer")
    public ModelAndView transfer(RedirectAttributes attributes, @PathVariable("login") String login,
                                 @RequestParam String from_currency, @RequestParam Float value,
                                 @RequestParam String to_login, @RequestParam String to_currency)
            throws JsonProcessingException {
        String transfer = transferService.transfer(login, from_currency, value, to_login, to_currency);
        if (!transfer.equals("OK")) {
            if (login.equals(to_login)) {
                attributes.addFlashAttribute("transferErrors", transfer);
            } else {
                attributes.addFlashAttribute("transferOtherErrors", transfer);
            }
        }
        return new ModelAndView("redirect:/main");
    }
}
