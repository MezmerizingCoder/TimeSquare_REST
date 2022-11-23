package com.timesquare.TimeSquare_REST.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JsonArray;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class EmailController {


    @PostMapping("/test")
    public String postTest(@RequestBody JSONObject json){
        Email from = new Email("mezmerizingcoder444@gmail.com");
        Email to = new Email("faustinopauljustine@gmail.com");
        String subject = "Ayee";
        Content content = new Content("text/plain", "I'm replacing the <strong>body tag</strong>");

//        Mail mail = new Mail(from, subject, to, content);
        Mail mail = new Mail();
        mail.setFrom(from);
        Personalization personalization = new Personalization();
        personalization.addTo(to);
//        personalization.addDynamicTemplateData("name", "paul");

        //Total Sales
//        personalization.addDynamicTemplateData("totalSales", 20500);
        personalization.addDynamicTemplateData("totalSales", json.get("totalSales"));
//        personalization.addDynamicTemplateData("totalOrders", 25);
        personalization.addDynamicTemplateData("totalService", json.get("totalService"));
        personalization.addDynamicTemplateData("totalRefund", json.get("totalRefund"));

        //Ring Chart
//        personalization.addDynamicTemplateData("chartURL", "{type:'doughnut',data:{labels:['January','February','March','April','May'],datasets:[{data:[50,60,70,180,190]}]},options:{plugins:{doughnutlabel:{labels:[{text:'550',font:{size:20}},{text:'total'}]}}}}");
        personalization.addDynamicTemplateData("chartURL", json.get("chartURL"));

        //Reciept
        JSONObject object = new JSONObject();
        object.put("name", "Casio 123");
        object.put("order", 4);
        object.put("price", 1300);
        JSONObject object2 = new JSONObject();
        object2.put("name", "Rolex");
        object2.put("order", 2);
        object2 .put("price", 2000);

//        JSONArray orderHistory = (JSONArray) json.get("orderHistory");
//        orderHistory.forEach(e -> {
//
//        });



        JSONArray array = new JSONArray();
        array.add(object);
        array.add(object2);

        System.out.println(array.toString());

        personalization.addDynamicTemplateData("orderHistory", json.get("orderHistory"));


        mail.addPersonalization(personalization);


        mail.setReplyTo(from);
        mail.setTemplateId("d-5f68fda2f89244bb82b224515c8b1262");

//        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        SendGrid sg = new SendGrid((String) json.get("apiKey"));
        Request request = new Request();

        try{
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

        }catch(Exception e){

        }


        return "email sent ";
    }

    @GetMapping("/email")
    public String sendEmail() throws ExecutionException, InterruptedException {
        Email from = new Email("mezmerizingcoder444@gmail.com");
        Email to = new Email("faustinopauljustine@gmail.com");
        String subject = "Ayee";
        Content content = new Content("text/plain", "I'm replacing the <strong>body tag</strong>");

//        Mail mail = new Mail(from, subject, to, content);
        Mail mail = new Mail();
        mail.setFrom(from);
        Personalization personalization = new Personalization();
        personalization.addTo(to);
//        personalization.addDynamicTemplateData("name", "paul");

        //Total Sales
        personalization.addDynamicTemplateData("totalSales", 20500);
        personalization.addDynamicTemplateData("totalOrders", 25);

        //Ring Chart
        personalization.addDynamicTemplateData("chartURL", "{type:'doughnut',data:{labels:['January','February','March','April','May'],datasets:[{data:[50,60,70,180,190]}]},options:{plugins:{doughnutlabel:{labels:[{text:'550',font:{size:20}},{text:'total'}]}}}}");

        //Reciept
        JSONObject object = new JSONObject();
        object.put("name", "Casio 123");
        object.put("order", 4);
        object.put("price", 1300);
        JSONObject object2 = new JSONObject();
        object2.put("name", "Rolex");
        object2.put("order", 2);
        object2 .put("price", 2000);



        JSONArray array = new JSONArray();
        array.add(object);
        array.add(object2);

        System.out.println(array.toString());

        personalization.addDynamicTemplateData("orderHistory", array);


        mail.addPersonalization(personalization);


        mail.setReplyTo(from);
        mail.setTemplateId("d-5f68fda2f89244bb82b224515c8b1262");

//        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();

        try{
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

        }catch(Exception e){

        }


        return "email sent";
    }

    class Test {

        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
