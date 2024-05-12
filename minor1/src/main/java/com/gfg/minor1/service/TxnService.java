package com.gfg.minor1.service;

import com.gfg.minor1.exception.TxnException;
import com.gfg.minor1.model.*;
import com.gfg.minor1.repository.TxnRepository;
import com.gfg.minor1.request.TxnCreateRequest;
//import jakarta.transaction.Transactional;
import com.gfg.minor1.request.TxnReturnRequest;
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
    private TxnRepository txnRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private StudentService studentService;
    @Value("${student.valid.days}")
    private String validUpto;

    @Value("${student.delayed.finePerDay}")
    private int finePerDay;

    public Student filterStudent(StudentFilterType type, Operator operator, String value) throws TxnException {
        List<Student> studentList = studentService.filter(type, operator, value);
        if(studentList == null || studentList.isEmpty()){
            throw new TxnException("student does not belong to my library");

        }

        Student studentFromDB = studentList.get(0);
        return studentFromDB;
    }

    private Book filterBook(FilterType type, Operator operator, String value) throws TxnException {
        List<Book> bookList = bookService.filter(type, operator, value);
        if(bookList == null || bookList.isEmpty()){
            throw new TxnException("student does not belong to my library");

        }

        Book bookFromLib = bookList.get(0);
        return bookFromLib;
    }

    @Transactional(rollbackFor = {TxnException.class})
    public String create(TxnCreateRequest txnCreateRequest, Student student) throws TxnException {
        // 1 ) want to see if student is valid or not

        Student studentFromDB = filterStudent(StudentFilterType.CONTACT, Operator.EQUALS,student.getPhoneNo());
        Book bookFromLib = filterBook(FilterType.BOOK_NO, Operator.EQUALS, txnCreateRequest.getBookNo());

        if(bookFromLib.getStudent()!=null){
            throw new TxnException("Book has already assigned to someone else");
        }

        String txnid = UUID.randomUUID().toString();

//     *****   without using builder  ****
//        Txn txn = new Txn();
//        txn.setTxnId(txnId);
//        txn.setBook(bookFromLib);

        Txn txn = Txn.builder().
                student(studentFromDB).
                book(bookFromLib).
                txnId(txnid).
                paidAmount(txnCreateRequest.getAmount()).
                status(TxnStatus.ISSUED).
                build();

        txn = txnRepository.save(txn);
        bookFromLib.setStudent(studentFromDB);
        bookService.saveUpdate(bookFromLib);
        return txn.getTxnId();

    }

    public int calculateSettlementAmount(Txn txn) {
        long issueTime=  txn.getCreatedOn().getTime();
        long returnTime= System.currentTimeMillis();
        long timeDiff = returnTime-issueTime;
        int daysPassed =(int) TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
        if(daysPassed> Integer.valueOf(validUpto)){
            int fineAmount = (daysPassed-Integer.valueOf(validUpto))*finePerDay;
            return txn.getPaidAmount()-fineAmount;
        }
        return txn.getPaidAmount();

    }
    @Transactional(rollbackFor = {TxnException.class})
    public int returnBook(TxnReturnRequest txnReturnRequest) throws TxnException {
        Student studentFromDB = filterStudent(StudentFilterType.CONTACT, Operator.EQUALS, txnReturnRequest.getStudentContact());
        Book bookFromLib = filterBook(FilterType.BOOK_NO, Operator.EQUALS, txnReturnRequest.getBookNo());

        if(bookFromLib.getStudent() != null && bookFromLib.getStudent().equals(studentFromDB)){
            Txn txnFromDb =  txnRepository.findByTxnId(txnReturnRequest.getTxnId());

            if(txnFromDb == null){
                throw new TxnException("No txn has been found with this txnid.");
            }
            int amount = calculateSettlementAmount(txnFromDb);
            if(amount == txnFromDb.getPaidAmount()){
                txnFromDb.setStatus(TxnStatus.RETURNED);
            }else{
                txnFromDb.setStatus(TxnStatus.FINED);
            }
            txnFromDb.setPaidAmount(amount);  //here now amount is coming from calculateSettlementAmount

            // update the book, marking student null

            bookFromLib.setStudent(null); //here book is ready for you to reassign to someone else
            bookService.saveUpdate(bookFromLib);
            return amount;
        }
        else{
            throw new TxnException("Book is either not assigned to anyone, or may be assigned to someone else !");
        }

    }
}
