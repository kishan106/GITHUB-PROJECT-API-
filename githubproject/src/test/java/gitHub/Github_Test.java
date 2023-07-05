package gitHub;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;

import java.util.Base64;

import org.json.simple.JSONObject;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Github_Test {
	
	String baseurl="https://api.github.com";
	String content_sha;
	 String update_content_sha;
	 int autolink_id;


    @Test(priority=1)
    public void get_test() {
        JSONObject jon=new JSONObject();
        jon.put("name", "Hello-World");
        
        given().baseUri(baseurl)
        .header("Authorization","Bearer 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")
        .header("Content-Type","application/json")
       .body(jon)
       .when()
       .post("/user/repos")
       .then()
       .statusCode(201)
       .log()
       .all();
    }
    @Test(priority = 2)
    public void git_update_repository() {
    	JSONObject jon=new JSONObject();
        jon.put("name", "shiva");
        
        given().baseUri(baseurl)
        .header("Authorization","Bearer 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")
        .header("Content-Type","application/json").body(jon)
       .when()
       .patch("/repos/kishan106/Hello-World")
       .then()
       .statusCode(200)
       .log()
       .all();
    }

    @Test(priority = 3)
    public void git_delete_repository() {
        
        given().baseUri(baseurl)
        .header("Authorization","Bearer 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")
        .header("Content-Type","application/json")
       .when()
       .delete("/repos/kishan106/shiva")
       .then()
       .statusCode(204)
       .log()
       .all();
    }
    
    @Test(priority = 4)
    public void git_get_repository() {
        
        given().baseUri(baseurl)
        .header("Authorization","Bearer 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")
        .header("Content-Type","application/json")
       .when()
       .get("/repos/kishan106/kishan123")
       .then()
       .statusCode(200)
       .log()
       .all();
    }
    
    @Test(priority = 5)
    public void create_a_fork() {
        
        given().baseUri(baseurl)
        .header("Authorization","Bearer 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")
        .header("Content-Type","application/json")
       .when()
       .post("/repos/kanwarchalotra/AppiumFramework/forks")
       .then()
       .statusCode(202)
       .log()
       .all();
    }
    
    @Test(priority = 6)
    public void list_forks() {
        
        given().baseUri(baseurl)
        .header("Authorization","Bearer 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")
        .header("Content-Type","application/json")
       .when()
       .get("/repos/kanwarchalotra/AppiumFramework/forks")
       .then()
       .statusCode(200)
       .log()
       .all();
    }
    
    @Test(priority = 7)
    public void list_repositories() {
        
        given().baseUri(baseurl)
        .header("Authorization","Bearer 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")
        .header("Content-Type","application/json")
       .when()
       .get("/user/repos")
       .then()
       .statusCode(200)
       .log()
       .all();
    }
    
    @Test(priority = 8)
    public void list_repo_lang() {
        
        given().baseUri(baseurl)
        .header("Authorization","Bearer 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")
        .header("Content-Type","application/json")
       .when()
       .get("/repos/kishan106/kishan123/languages")
       .then()
       .statusCode(200)
       .log()
       .all();
    }
    
    @Test(priority = 9)
    public void list_public_repo() {
        
        given().baseUri(baseurl)
        .header("Authorization","Bearer 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")
        .header("Content-Type","application/json")
       .when()
       .get("/repositories")
       .then()
       .statusCode(200)
       .log()
       .all();
    }
    
    @Test(priority = 10)
	public void create_a_file_content() {
        String content = "bXkgbmV3IGZpbGUgY29dGVudHM=";
        String base64Content = Base64.getEncoder().encodeToString(content.getBytes());


		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", "my commit message");
		JSONObject committer = new JSONObject();
		committer.put("name", "Monalisa Octocat");
		committer.put("email", "octocat@github.com");
		jsonObject.put("committer", committer);
		jsonObject.put("content", base64Content);	
		
		Response resp = given().baseUri(baseurl)
				.header("Authorization", "Bearer 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")
				.contentType("application/x-www-form-urlencoded")
				.body(jsonObject.toJSONString()).when()
				.put("/repos/kishan106/kishan123/contents/open666.txt").then().log().all().extract().response();
		// String responsive=resp.asString();
		System.out.println("sha for creating a file ::" + resp.path("content.sha"));
		content_sha = resp.path("content.sha").toString();
	}	
    
    @Test(priority = 11)
	public void update_a_file_content() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", "a new commit message");
		JSONObject committer = new JSONObject();
		committer.put("name", "Monalisa Octocat");
		committer.put("email", "octocat@github.com");
		jsonObject.put("committer", committer);
		jsonObject.put("content", "bXkgdXBkYXRlZCBmaWxlIGNvbnRlbnRz");
		System.out.println("content_sha"+content_sha);
		jsonObject.put("sha", content_sha);

		Response resp = given().baseUri(baseurl)
				.header("Authorization", "Bearer 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")
				.contentType(ContentType.JSON).body(jsonObject.toJSONString()).when()
				.put("/repos/kishan106/kishan123/contents/open666.txt").then().log().all().extract().response();

       //String responsive=resp.asString();

		System.out.println("sha path for updating a file ::" + resp.path("content.sha"));
		update_content_sha = resp.path("content.sha").toString();
	}
    
   
    @Test(priority = 12)
	public void delete_a_file_content() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("message", "a new commit message");

		JSONObject committer = new JSONObject();
		committer.put("name", "Monalisa Octocat");
		committer.put("email", "octocat@github.com");

		jsonObject.put("committer", committer);

		jsonObject.put("content", "bXkgdXBkYXRlZCBmaWxlIGNvbnRlbnRz");
		
		System.out.println("updated content shar"+update_content_sha);
		jsonObject.put("sha", update_content_sha);

		given().baseUri(baseurl).header("Authorization", "Bearer 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")
				.contentType(ContentType.JSON).body(jsonObject.toJSONString()).when()
				.delete("/repos/kishan106/kishan123/contents/open666.txt").then().log().all();

	}
    
    @Test(priority = 13)
    public void list_repo_tags() {
        
        given().baseUri(baseurl)
        .header("Authorization","Bearer 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")
        .header("Content-Type","application/json")
       .when()
       .get("/repos/kishan106/kishan123/tags")
       .then()
       .statusCode(200)
       .log()
       .all();
    }
    
    @Test(priority = 14)
    public void git_create_an_autolink() {
        JSONObject jon = new JSONObject();
        jon.put("key_prefix", "TICKET-");
        jon.put("url_template", "https://example.com/TICKET?query=<num>");
        jon.put("is_alphanumeric", true);
        
        Response resp=given().baseUri(baseurl).header("Authorization", "Bearer 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")
                .header("Content-Type", "application/json").body(jon)
                .post("/repos/kishan106/kishan123/autolinks").then().statusCode(201).log().all().extract().response() ;
 
        autolink_id=resp.path("id");
        
    }
    
    @Test(priority = 15)
    public void get_an_autolink_from_a_repo() {

        given().baseUri(baseurl).header("Authorization", "Bearer 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")
                .header("Content-Type", "application/json")
                .get("/repos/kishan106/kishan123/autolinks/"+autolink_id+"").then().statusCode(200).log().all();
    }
    
    @Test(priority = 16)
    public void git_an_delete_autolink_from_a_repo() {

        given().baseUri(baseurl).header("Authorization", "Bearer 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")
                .header("Content-Type", "application/json")
                .delete("/repos/kishan106/kishan123/autolinks/+"+autolink_id+"").then().statusCode(204).log().all();
    }
    
    
    
    @Test(priority = 17)
    public void getall_repo_topics() {
        
        given().baseUri(baseurl)
        .header("Authorization","Bearer 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")
        .header("Content-Type","application/json")
       .when()
       .get("/repos/kishan106/kishan123/topics")
       .then()
       .statusCode(200)
       .log()
       .all();
    }
    
    @Test(priority = 18)
    public void git_replace_a_repo_topic() {
        String jsonbody = "{\"names\":[\"octocat\",\"atom\",\"electron\",\"api\"]}";
        given().baseUri(baseurl).header("Authorization", "Bearer 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")
                .contentType(ContentType.JSON).body(jsonbody).when().put("/repos/kishan106/kishan123/topics").then()
                .statusCode(200).log().all();
    }
    
    
    
    
    
    
    
   
    
    
    
    
    
    
    
    
    
	
    
}
