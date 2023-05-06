package ma.enset.controlejee.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.controlejee.dao.entities.Abonnement;
import ma.enset.controlejee.dao.entities.Client;
import ma.enset.controlejee.dao.entities.TypeAbonnement;
import ma.enset.controlejee.dao.repositories.AbonnementRepository;
import ma.enset.controlejee.dao.repositories.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class AbonnementController {

    private AbonnementRepository abonnementRepository;
    @GetMapping("/abonnement/index")
    public String Abonnement(Model model,
                         @RequestParam(name = "page",defaultValue = "0") int page,
                         @RequestParam(name = "keyWord",defaultValue = "") String keyWord,
                         @RequestParam(name = "size",defaultValue = "5") int size){
        Page<Abonnement> abonnements = abonnementRepository.findByClient_NomContains(keyWord, PageRequest.of(page,size));
        // envoyer les donnees a la page HTML
        model.addAttribute("abonnement",abonnements.getContent());
        model.addAttribute("pages",new int[abonnements.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyWord",keyWord);
        return "abonnement";
    }

    @GetMapping("/user/abonnement")
    @ResponseBody
    public List<Abonnement> abonnementsList(){
        return  abonnementRepository.findAll();
    }
    @GetMapping("/admin/fromAbonnement")
    public String fromClients(Model model){
        model.addAttribute("client",new Client());
        return "formClients";
    }

    @PostMapping("/admin/saveAbonnement")
    public String Save(Model model, @Valid Abonnement abonnement , BindingResult bindingResult, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = " ") String KeyWord)
    {
        if(bindingResult.hasErrors()) return "formAbonnement";
        abonnementRepository.save(abonnement);
        return "redirect:/abonnement/index?page="+page+"&KeyWord="+KeyWord;
    }

    @GetMapping("/admin/editAbonnement")
    public String formEditClient(Model model,String id,String KeyWord,int page){
        Abonnement abonnement=abonnementRepository.findById(id).orElse(null);
        if (abonnement==null) throw  new RuntimeException("Abonnement non introuvable");
        model.addAttribute("abonnement",abonnement);
        model.addAttribute("keyword",KeyWord);
        model.addAttribute("page",page);
        return "editAbonnement";
    }
    @GetMapping("/admin/deleteAbonnement")
    public  String delete(@RequestParam("id") String id,@RequestParam(name="KeyWord",defaultValue = "") String KeyWord, @RequestParam(name="page",defaultValue = "0")  int page){
        abonnementRepository.deleteById(id);
        return "redirect:/abonnement/index";

    }
}
