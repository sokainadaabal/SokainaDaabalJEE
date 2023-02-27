package ma.enset.ext;

import ma.enset.dao.IDao;
import org.springframework.stereotype.Component;

@Component("dao2")
public class DaoImplVWeb implements IDao {
    @Override
    public double getdate() {
        System.out.println("Version web Service");

        return 90;
    }
}
