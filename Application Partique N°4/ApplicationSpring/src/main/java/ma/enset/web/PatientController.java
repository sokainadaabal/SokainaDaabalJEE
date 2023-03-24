package ma.enset.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.entities.Patient;
import ma.enset.repositories.PatientRepository;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// montionner qu'il est un controller
@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/index")
    public String patient(Model model,
                          @RequestParam(name="page",defaultValue = "0") int page // permet de identifier de quelpage
                          ,@RequestParam(name="size",defaultValue = "5") int size,
                          @RequestParam(name = "KeyWord",defaultValue = "") String serachName){
        Page<Patient> pagePatients= patientRepository.findByNomContains(serachName,PageRequest.of(page,size));
        // envoyer les donnees a la page html
        model.addAttribute("patients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyWord",serachName);
        return "patients";
    }


    @GetMapping("delete")
    public  String delete(@RequestParam("id") Long id,@RequestParam(name="KeyWord",defaultValue = "") String KeyWord, @RequestParam(name="page",defaultValue = "0")  int page){
        patientRepository.deleteById(id);
        return "redirect:/index";

    }
    @GetMapping("/")
    public String Home(){
        return "redirect:/index";
    }
    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> listPatients(){
        return patientRepository.findAll();
    }

    @GetMapping("/formPatients")
    public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }
   @PostMapping("/save")
    public String save(Model model, @Valid  Patient patient , BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "formPatients";
   }


}
