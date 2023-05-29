package ma.enset.ebankingbackend.repositories;

import ma.enset.ebankingbackend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
  // permet de retourner une liste operation realise dans le compte recherche par le compte
  List<AccountOperation> findByBankAccountId(String accountId);

  // Pagination
  Page<AccountOperation> findByBankAccountId(String accountId, Pageable pageable);

  Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String accountId, Pageable pageable);

}
