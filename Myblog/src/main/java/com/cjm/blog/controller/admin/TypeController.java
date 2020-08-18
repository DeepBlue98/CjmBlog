package com.cjm.blog.controller.admin;

import com.cjm.blog.po.Type;
import com.cjm.blog.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    ITypeService iTypeService;

    @GetMapping("/types")
    public String types(Model model,
                        @PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("pages",iTypeService.listType(pageable));
        return "admin/types";
    }

    @RequestMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type tmp=iTypeService.getTypebyname(type.getName());
        if (tmp != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type type1= iTypeService.saveType(type);
        if (type1==null){
            attributes.addFlashAttribute("message","操作失败！");
        }else{
            attributes.addFlashAttribute("message","操作成功！");
        }
        return "redirect:/admin/types";
    }

    //选定一个来修改的时候
    @GetMapping("/types/{id}/input")
    public String toEdit(@PathVariable Long id, Model model){
        model.addAttribute("type",iTypeService.getType(id));
        return "admin/types-input";
    }


    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) {
        Type type1 = iTypeService.getTypebyname(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = iTypeService.updateType(id,type);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
   public String delete(@PathVariable Long id,RedirectAttributes attributes){
        iTypeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
   }
}
