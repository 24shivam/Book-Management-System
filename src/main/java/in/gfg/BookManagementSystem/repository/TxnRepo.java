package in.gfg.BookManagementSystem.repository;

import in.gfg.BookManagementSystem.model.Author;
import in.gfg.BookManagementSystem.model.Txn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TxnRepo extends JpaRepository<Txn,Integer> {

    Txn findByTxnId(String tnxId);

}
