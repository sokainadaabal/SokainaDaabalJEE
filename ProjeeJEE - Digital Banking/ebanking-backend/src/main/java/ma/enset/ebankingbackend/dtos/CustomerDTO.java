package ma.enset.ebankingbackend.dtos;

import lombok.Data;

@Data // getter et setters
public class CustomerDTO {
  private Long id;
  private String name;
  private String email;
}
