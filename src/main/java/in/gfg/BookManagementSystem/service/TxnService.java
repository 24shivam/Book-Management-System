/*
package in.gfg.BookManagementSystem.service;
import in.gfg.BookManagementSystem.exception.TxnException;
import in.gfg.BookManagementSystem.model.*;
import in.gfg.BookManagementSystem.repository.TxnRepo;
import in.gfg.BookManagementSystem.requestDto.TxnCreateRequest;
import in.gfg.BookManagementSystem.requestDto.TxnReturnRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TxnService {

    @Autowired
    private TxnRepo txnRepo;

    @Autowired
    private BookService bookService;

    @Autowired
    private StudentService studentService;

    @Value("${student.valid.days}")
    private String validUpto;

    @Value("${student.delayed.finePerDay}")
    private int finePerDay;

    private Student filterStudent(FilterType type, Operators operators,String value) throws TxnException {

        List<Student> studentList= studentService.filter(type,operators,value);

        if(studentList==null||studentList.isEmpty())
        {
            throw  new TxnException("Student rxdtcf does not belongs to my library .");
        }

        Student studentFromDB= studentList.get(0);
        return studentFromDB;
    }

    private  Book filterBook(FilterType type, Operators operators,String value) throws TxnException {

        List<Book> bookList = bookService.filter(type, operators, value);

        if(bookList==null||bookList.isEmpty()) // check for null and empty
        {
            throw  new TxnException("Book does not belongs to my library .");
        }

        Book bookFromLib= bookList.get(0); //bookList.get(0).getStudent()!=null -->for check the book is present or not.
        if(bookFromLib.getStudent()!=null){
            throw  new TxnException("Book has already assigned to someone else");
        }
        return bookFromLib;
    }




    @Transactional(rollbackFor = {TxnException.class})//at a one time only single user are able to perform the transactional operations other will get a run time error
    public String create(TxnCreateRequest txnCreateRequest) throws TxnException {

        //1- want to see if the student is valid or not
       Student studentFromDB= filterStudent(FilterType.STUDENT_PHONE, Operators.EQUALS,txnCreateRequest.getStudentContact());


        //check for the book
        Book bookFromLib = filterBook(FilterType.BOOK_NO, Operators.EQUALS,txnCreateRequest.getBookNo());





        if(bookFromLib.getStudent()!=null){
            throw  new TxnException("Book has already assigned to someone else");
        }


        String tnxid= UUID.randomUUID().toString();
        //perform the transaction
        Txn txn= Txn.builder()
                .student(studentFromDB)
                .book(bookFromLib)
                .txnId(tnxid)
                .paidAmount(txnCreateRequest.getAmount())
                .txnStatus(TxnStatus.ISSUED)
                .build();


        txn= txnRepo.save(txn);

        System.out.println("transaction id : "+tnxid);

        //update book_table   the student_id column  after the txn performs

        bookFromLib.setStudent(studentFromDB);
        bookService.saveUpdate(bookFromLib);
        return txn.getTxnId();

    }




    @Transactional(rollbackFor = {TxnException.class})
    public int returnBook(TxnReturnRequest txnReturnRequest) throws TxnException {

        Student studentFromDB= filterStudent(FilterType.STUDENT_PHONE, Operators.EQUALS,txnReturnRequest.getStudentContact());

        Book bookFromLib = filterBook(FilterType.BOOK_NO, Operators.EQUALS,txnReturnRequest.getBookNo());
        if(bookFromLib.getStudent()!=null && bookFromLib.getStudent().equals(studentFromDB)){

         Txn txnFromDB=   txnRepo.findByTxnId(txnReturnRequest.getTxnId());
         if(txnFromDB == null){
             throw new TxnException(" no txn has been found with this txnId");
         }
         int amount=calculateSettlementAmount(txnFromDB);
             if (amount==txnFromDB.getPaidAmount()){
                 txnFromDB.setTxnStatus(TxnStatus.RETURNED);
             }else {
                 txnFromDB.setTxnStatus(TxnStatus.FINED);
             }

             txnFromDB.setPaidAmount(amount);

             //update the book , marking student null
            bookFromLib.setStudent(null);
            bookService.saveUpdate(bookFromLib);
            return amount;
        }
        else {
            throw new TxnException("Book is either not assign to anyone, or may be assigned to some else!! ");
        }


    }

    private int calculateSettlementAmount(Txn txn) {

       long issuedTime= txn.getCreatedOn().getTime();

       long returnTime= System.currentTimeMillis();
       long timeDiff= returnTime-issuedTime;

       int daysPassed =(int) TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
       if (daysPassed>Integer.valueOf(validUpto)){
           int fineAmount= (daysPassed-Integer.valueOf(validUpto)) *finePerDay;
           return txn.getPaidAmount()-fineAmount;
       }
        return txn.getPaidAmount();
    }


}
*/
//----------------
package in.gfg.BookManagementSystem.service;

import in.gfg.BookManagementSystem.exception.DataNotFound;
import in.gfg.BookManagementSystem.exception.TxnException;
import in.gfg.BookManagementSystem.model.*;
import in.gfg.BookManagementSystem.repository.TxnRepo;
import in.gfg.BookManagementSystem.requestDto.TxnCreateRequest;
import in.gfg.BookManagementSystem.requestDto.TxnReturnRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TxnService {

    @Autowired
    private TxnRepo txnRepo;

    @Autowired
    private BookService bookService;

    @Autowired
    private StudentService studentService;

    @Value("${student.valid.days}")
    private String validUpto;

    @Value("${student.delayed.finePerDay}")
    private int finePerDay;

    private Student filterStudent(FilterType type, Operators operators, String value) throws TxnException, DataNotFound {
        // Log input parameters
        System.out.println("Filtering student with type: " + type + ", operators: " + operators + ", value: " + value);

        List<Student> studentList = studentService.filter(type, operators, value);

        if (studentList == null || studentList.isEmpty()) {
            throw new TxnException("Student " + value + " does not belong to my library.");
        }

        Student studentFromDB = studentList.get(0);
        // Log the result
        System.out.println("Student found: " + studentFromDB);
        return studentFromDB;
    }

    private Book filterBook(FilterType type, Operators operators, String value) throws TxnException, DataNotFound {
        // Log input parameters
        System.out.println("Filtering book with type: " + type + ", operators: " + operators + ", value: " + value);

        List<Book> bookList = bookService.filter(type, operators, value);

        if (bookList == null || bookList.isEmpty()) {
            throw new TxnException("Book does not belong to my library.");
        }

        Book bookFromLib = bookList.get(0);

        // Log the result
        System.out.println("Book found: " + bookFromLib);
        return bookFromLib;
    }

    @Transactional(rollbackFor = {TxnException.class})
    public String create(TxnCreateRequest txnCreateRequest,Student student) throws TxnException, DataNotFound {
        // 1 - Check if the student is valid
        Student studentFromDB = filterStudent(FilterType.STUDENT_PHONE, Operators.EQUALS, student.getPhoneNo());

        // 2 - Check for the book
        Book bookFromLib = filterBook(FilterType.BOOK_NO, Operators.EQUALS, txnCreateRequest.getBookNo());

        if (bookFromLib.getStudent() != null) {
            throw new TxnException("Book has already been assigned to someone else");
        }

        String txnId = UUID.randomUUID().toString();
        // Perform the transaction
        Txn txn = Txn.builder()
                .student(studentFromDB)
                .book(bookFromLib)
                .txnId(txnId)
                .paidAmount(txnCreateRequest.getAmount())
                .txnStatus(TxnStatus.ISSUED)
                .build();

        txn = txnRepo.save(txn);
        System.out.println("Transaction ID: " + txnId);

        // Update book table: the student_id column after the transaction performs
        bookFromLib.setStudent(studentFromDB);
        bookService.saveUpdate(bookFromLib);
        return txn.getTxnId();
    }

    @Transactional(rollbackFor = {TxnException.class})
    public int returnBook(TxnReturnRequest txnReturnRequest) throws TxnException, DataNotFound {
        Student studentFromDB = filterStudent(FilterType.STUDENT_PHONE, Operators.EQUALS, txnReturnRequest.getPhoneNo());

        Book bookFromLib = filterBook(FilterType.BOOK_NO, Operators.EQUALS, txnReturnRequest.getBookNo());


        if (bookFromLib.getStudent() != null && bookFromLib.getStudent().equals(studentFromDB)) {
            Txn txnFromDB = txnRepo.findByTxnId(txnReturnRequest.getTxnId());

            if (txnFromDB == null) {
                throw new TxnException("No transaction has been found with this transaction ID");
            }
            int amount = calculateSettlementAmount(txnFromDB);
            if (amount == txnFromDB.getPaidAmount()) {
                txnFromDB.setTxnStatus(TxnStatus.RETURNED);
            } else {
                txnFromDB.setTxnStatus(TxnStatus.FINED);
            }

            txnFromDB.setPaidAmount(amount);

            // Update the book, marking student as null
            bookFromLib.setStudent(null);
            bookService.saveUpdate(bookFromLib);
            return amount;
        } else {
            throw new TxnException("Book is either not assigned to anyone or may be assigned to someone else!");
        }
    }

    private int calculateSettlementAmount(Txn txn) {
        long issuedTime = txn.getCreatedOn().getTime();
        long returnTime = System.currentTimeMillis();
        long timeDiff = returnTime - issuedTime;

        int daysPassed = (int) TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
        if (daysPassed > Integer.valueOf(validUpto)) {
            int fineAmount = (daysPassed - Integer.valueOf(validUpto)) * finePerDay;
            return txn.getPaidAmount() - fineAmount;
        }
        return txn.getPaidAmount();
    }
}


