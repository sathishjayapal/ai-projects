package me.sathish.ai_project.sathishaiquery.lookup_user_questions;

import jakarta.validation.Valid;
import me.sathish.ai_project.base.util.WebUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/lookupUserQuestionss")
public class LookupUserQuestionsController {

    private final LookupUserQuestionsService lookupUserQuestionsService;

    public LookupUserQuestionsController(
            final LookupUserQuestionsService lookupUserQuestionsService) {
        this.lookupUserQuestionsService = lookupUserQuestionsService;
    }

    @GetMapping
    public String list(@RequestParam(name = "filter", required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable,
            final Model model) {
        final Page<LookupUserQuestionsDTO> lookupUserQuestionses = lookupUserQuestionsService.findAll(filter, pageable);
        model.addAttribute("lookupUserQuestionses", lookupUserQuestionses);
        model.addAttribute("filter", filter);
        model.addAttribute("paginationModel", WebUtils.getPaginationModel(lookupUserQuestionses));
        return "lookupUserQuestions/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("lookupUserQuestions") final LookupUserQuestionsDTO lookupUserQuestionsDTO) {
        return "lookupUserQuestions/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("lookupUserQuestions") @Valid final LookupUserQuestionsDTO lookupUserQuestionsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "lookupUserQuestions/add";
        }
        lookupUserQuestionsService.create(lookupUserQuestionsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("lookupUserQuestions.create.success"));
        return "redirect:/lookupUserQuestionss";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("lookupUserQuestions", lookupUserQuestionsService.get(id));
        return "lookupUserQuestions/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("lookupUserQuestions") @Valid final LookupUserQuestionsDTO lookupUserQuestionsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "lookupUserQuestions/edit";
        }
        lookupUserQuestionsService.update(id, lookupUserQuestionsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("lookupUserQuestions.update.success"));
        return "redirect:/lookupUserQuestionss";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        lookupUserQuestionsService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("lookupUserQuestions.delete.success"));
        return "redirect:/lookupUserQuestionss";
    }

}
