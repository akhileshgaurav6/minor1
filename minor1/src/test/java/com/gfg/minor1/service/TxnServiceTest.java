package com.gfg.minor1.service;

import com.gfg.minor1.exception.TxnException;
import com.gfg.minor1.model.Student;
import com.gfg.minor1.model.Txn;
import com.gfg.minor1.repository.TxnRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TxnServiceTest {

    @InjectMocks
    private TxnService txnService;

    @Mock
    private TxnRepository txnRepository;
    @Mock
    private BookService bookService;
    @Mock
    private StudentService studentService;

    @Before
    public void setUp(){
//        txnService = new TxnService();
//        StudentService studentService = new StudentService();

        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(txnService, "validUpto", "14");
        ReflectionTestUtils.setField(txnService, "finePerDay", 1);
    }

    @Test
    public void testCalculateSettlementAmount() throws ParseException {
        Date date = new SimpleDateFormat("YYYY-MM-DD").parse("2023-01-15");
        Txn txn = Txn.builder().createdOn(date).paidAmount(200).build();
        int amount = txnService.calculateSettlementAmount(txn);
        Assert.assertEquals(200,amount);

    }

    @Test(expected = TxnException.class)
    public void testFilterStudent() throws TxnException {
        when(studentService.filter(any(),any(),any())).thenReturn(null);
        txnService.filterStudent(any(),any(),any());

    }

    @Test(expected = TxnException.class)
    public void testFilterStudentEmptyList() throws TxnException {
        when(studentService.filter(any(),any(),any())).thenReturn(new ArrayList<>());
        txnService.filterStudent(any(),any(),any());

    }

    @Test(expected = TxnException.class)
    public void testFilterStudentListWithData() throws TxnException {
        List<Student> list = new ArrayList<>();
        Student student1 = Student.builder().id(1).build();
        list.add(student1);
        Student student2 = Student.builder().id(2).build();
        list.add(student2);
        when(studentService.filter(any(),any(),any())).thenReturn(list);
        Student student = txnService.filterStudent(any(),any(),any());
        Assert.assertEquals(student1.getId(),student2.getId());

    }

    public void testReturnBook(){
        Txn txn = Txn.builder().createdOn(new Date()).paidAmount(200).build();

//        txnService.returnBook(txn);

    }
}
