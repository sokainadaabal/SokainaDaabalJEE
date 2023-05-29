package ma.enset.backendSokainaDaabal.dtos;

import lombok.Data;
import ma.enset.backendSokainaDaabal.enums.TypeTicket;


@Data

public class TicketDTO {

    private  Long id;
    private int numPlace;
    private TypeTicket type;
    private VolDTO vol;
}
