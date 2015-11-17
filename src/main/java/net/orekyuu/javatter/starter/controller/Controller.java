package net.orekyuu.javatter.starter.controller;

import net.orekyuu.javatter.starter.service.ProjectZipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private ProjectZipService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show() {
        return "app";
    }

    @ModelAttribute
    public StarterForm starterForm() {
        return new StarterForm();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String generate(Model model, @Validated StarterForm form, BindingResult result) {
        if (result.hasErrors()) {
            List<String> collect = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", collect);
            return "app";
        }
        return "forward:/download";
    }

    @RequestMapping(value = "/download", method = {RequestMethod.POST})
    public void downloadZip(@Validated StarterForm form, BindingResult result, HttpServletResponse res) {
        if (result.hasErrors()) {
            List<String> collect = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
        }
        res.setHeader("Content-Disposition", "attachment; filename=" + form.getPluginName() + ".zip");
        res.setHeader("Content-Transfer-Encoding", "binary");
        res.setContentType("application/octet-stream;");
        try {
            service.generate(form, new BufferedOutputStream(res.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
