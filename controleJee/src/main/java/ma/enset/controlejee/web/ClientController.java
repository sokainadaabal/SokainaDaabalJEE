package ma.enset.controlejee.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import ma.enset.controlejee.dao.entities.Client;
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
public class ClientController {

    private ClientRepository clientRepository;

    @GetMapping("/client/index")
    public String Client(Model model,
                            @RequestParam(name = "page",defaultValue = "0") int page,
                            @RequestParam(name = "keyWord",defaultValue ="") String keyWord,
                            @RequestParam(name = "size",defaultValue = "5") int size){
        Page<Client> clientPage = clientRepository.findByNomContains(keyWord, PageRequest.of(page,size));
        // envoyer les donnees a la page HTML
        model.addAttribute("clients",clientPage.getContent());
        model.addAttribute("pages",new int[clientPage.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyWord",keyWord);
        return "clients";
    }

    @GetMapping("/")
    public String Home(){
        return "home";
    }
    @GetMapping("/client/")
    @ResponseBody
    public List<Client> clientList(){
        return  clientRepository.findAll();
    }
    @GetMapping("/admin/fromClient")
    public String fromClients(Model model){
        model.addAttribute("client",new Client());
        return "formClients";
    }

    @PostMapping("/admin/saveClient")
    public String Save(Model model, @Valid Client client , BindingResult bindingResult, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = " ") String KeyWord)
    {
        if(bindingResult.hasErrors()) return "formClients";
        clientRepository.save(client);
        return "redirect:/client/index?page="+page+"&KeyWord="+KeyWord;
    }
    @GetMapping("/admin/deleteClient")
    public  String delete(@RequestParam("id") Long id,@RequestParam(name="KeyWord",defaultValue = "") String KeyWord, @RequestParam(name="page",defaultValue = "0")  int page){
        clientRepository.deleteById(id);
        return "redirect:/client/index";

    }

    @GetMapping("/admin/editClient")
    public String formEditClient(Model model,Long id,String KeyWord,int page){
        Client client=clientRepository.findById(id).orElse(null);
        if (client==null) throw  new RuntimeException("Client introuvable");
        model.addAttribute("client",client);
        model.addAttribute("keyword",KeyWord);
        model.addAttribute("page",page);
        return "editClient";
    }

    @PostMapping("/admin/updateClient")
    public String updateClient(@Valid Client client){
        Client clientupdate = clientRepository.findById(client.getId()).get();
        clientupdate.setNom(client.getNom());
        clientupdate.setUsername(client.getUsername());
        clientupdate.setEmail(client.getEmail());
        clientRepository.save(client);
        return "redirect:/client/index";
    }
}
