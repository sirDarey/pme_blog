package com.pme.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DocsConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setName("PME Community");

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8201");
        localServer.setDescription("Local Server URL");

        Info info = new Info()
                .title("PME-BLOG API DOCUMENTATION")
                .contact(contact)
                .version("1.0")
                .description("This API exposes endpoints for the following: </br>" +
                        "<ol>" +
                        "  <li>User's Sign Up </li>" +
                        "  <li>Verify Email on Sign Up via OTP</li>" +
                        "  <li>Getting All Posts By Anyone</li>" +
                        "  <li>Getting A Specific Post By Anyone</li>" +
                        "  <li>Creating a Post By Signed-In Users</li>" +
                        "  <li>Getting All Posts by a User</li>" +
                        "</ol> " +

                        "</br><b>The Endpoints at 5 and 6 are secured; A user must be logged in to access them</b>" +
                        "<br><br>The authentication used is 'Basic Auth'; thus for the authenticated requests, " +
                        "a user must enter a username and a password as a 'Basic Auth' type. Also, the <i>'user_id'</i> " +
                        "must be passed as request header param. All are clearly stated in the docs." +

                        "</br><br>For the email OTP to work, navigate to the application.properties file in the " +
                        "the project and set the email smtp parameters needed; the template is there already, " +
                        "you just have to refactor a few things and you're good to go." +
                        "</br>Still on the smtp configuration, navigate to this file in the project: " +
                        "<i>/com/pme/token/service/EmailOTPService.java</i> " +
                        "Locate the function: <i>void sendOTPtoEmail</i> " +
                        "</br>Then, locate the line: " +
                        "<br><i>helper.setFrom(\"****SENDER-EMAIL*****\", \"pme-blog\"); //change sender mail\n</i>" +
                        "</br> As seen from the comment, change the Sender Email and this should match the value of " +
                        "<i>spring.mail.username</i> that was declared in application.properties file" +

                        "</br><br>Before running the application, create a new database named: 'pme_blog'" +
                        "</br>At application startup, all tables are created automatically and all posts from " +
                        "the other blog are IMPORTED." +

                        "<br><br>That's pretty much everything; you can go ahead to run the application.")
                ;

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}
