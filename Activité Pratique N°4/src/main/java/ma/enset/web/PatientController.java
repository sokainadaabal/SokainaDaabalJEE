package ma.enset.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.entities.Patient;
import ma.enset.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

// mentionner qu'il est un controller
@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/user/index")
    public String patient(Model model,
                          @RequestParam(name="page",defaultValue = "0") int page // permet de identifier de quel page
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



    @GetMapping("/admin/delete")
    public  String delete(@RequestParam("id") Long id,@RequestParam(name="KeyWord",defaultValue = "") String KeyWord, @RequestParam(name="page",defaultValue = "0")  int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index";

    }
    @GetMapping("/")
    public String Home(){
        return "home";
    }
    @GetMapping("/user/patient")
    @ResponseBody
    public List<Patient> listPatients(){
        return patientRepository.findAll();
    }

    @GetMapping("/admin/formPatients")
    public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }
   @PostMapping("/admin/save")
    public String save(Model model, @Valid Patient patient , BindingResult bindingResult, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = " ") String KeyWord){
        if(bindingResult.hasErrors()) return "formPatients";
        Patient patient1 = new Patient();
        patient1.setId(patient.getId());
        patient1.setNom(patient.getNom());
        patient1.setDateNaissance(patient.getDateNaissance());
        patient1.setScore(patient.getScore());
        patient1.setMalade(patient.isMalade());
        patientRepository.save(patient1);
        return "redirect:/user/index?page="+page+"&KeyWord="+KeyWord;
   }

   @GetMapping("/admin/editPatient")
   public String formEditPatients(Model model,Long id,String KeyWord,int page){
        Patient patient=patientRepository.findById(id).orElse(null);
        if (patient==null) throw  new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("keyword",KeyWord);
        model.addAttribute("page",page);
        return "editPatients";
   }


}
