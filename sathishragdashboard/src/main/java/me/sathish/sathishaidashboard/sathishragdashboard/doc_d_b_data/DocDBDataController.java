package me.sathish.sathishaidashboard.sathishragdashboard.doc_d_b_data;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import me.sathish.sathishaidashboard.base.file.FileData;
import me.sathish.sathishaidashboard.base.file.FileDataService;
import me.sathish.sathishaidashboard.base.util.JsonStringFormatter;
import me.sathish.sathishaidashboard.base.util.WebUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/docDBDatas")
public class DocDBDataController {

    private final DocDBDataService docDBDataService;
    private final ObjectMapper objectMapper;
    private final FileDataService fileDataService;

    public DocDBDataController(final DocDBDataService docDBDataService,
            final ObjectMapper objectMapper, final FileDataService fileDataService) {
        this.docDBDataService = docDBDataService;
        this.objectMapper = objectMapper;
        this.fileDataService = fileDataService;
    }

    @InitBinder
    public void jsonFormatting(final WebDataBinder binder) {
        binder.addCustomFormatter(new JsonStringFormatter<FileData>(objectMapper) {
        }, "fileName");
    }

    @GetMapping
    public String list(@RequestParam(name = "filter", required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable,
            final Model model) {
        final Page<DocDBDataDTO> docDBDatas = docDBDataService.findAll(filter, pageable);
        model.addAttribute("docDBDatas", docDBDatas);
        model.addAttribute("filter", filter);
        model.addAttribute("paginationModel", WebUtils.getPaginationModel(docDBDatas));
        return "docDBData/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("docDBData") final DocDBDataDTO docDBDataDTO) {
        return "docDBData/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("docDBData") @Valid final DocDBDataDTO docDBDataDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "docDBData/add";
        }
        docDBDataService.create(docDBDataDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("docDBData.create.success"));
        return "redirect:/docDBDatas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("docDBData", docDBDataService.get(id));
        model.addAttribute("withDownloads", true);
        return "docDBData/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("docDBData") @Valid final DocDBDataDTO docDBDataDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "docDBData/edit";
        }
        docDBDataService.update(id, docDBDataDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("docDBData.update.success"));
        return "redirect:/docDBDatas";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        docDBDataService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("docDBData.delete.success"));
        return "redirect:/docDBDatas";
    }

    @GetMapping("/{id}/fileName/{filename}")
    public ResponseEntity<byte[]> downloadFileName(@PathVariable(name = "id") final Long id) {
        final DocDBDataDTO docDBDataDTO = docDBDataService.get(id);
        return fileDataService.provideDownload(docDBDataDTO.getFileName());
    }

}
