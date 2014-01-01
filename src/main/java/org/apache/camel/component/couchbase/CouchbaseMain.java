package org.apache.camel.component.couchbase;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.View;
import com.couchbase.client.protocol.views.ViewResponse;
import com.couchbase.client.protocol.views.ViewRow;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class CouchbaseMain {

    public static void main(String... args) throws Exception {

        /*
        Main main = new Main();
        main.enableHangupSupport();
        //main.addRouteBuilder(new MyRouteBuilder());
        main.addRouteBuilder(new CouchBaseRoute());
        main.run(args);

        */

        List<URI> hosts = Arrays.asList(
                new URI("http://127.0.0.1:8091/pools")
        );

        // Name of the Bucket to connect to
        String bucket = "beer-sample";

        // Password of the bucket (empty) string if none
        String password = "";

        // Connect to the Cluster
        CouchbaseClient client = new CouchbaseClient(hosts, bucket, password);

        // Store a Document
        //client.set("my-first-document", "Hello Couchbase!").get();

        // Retrieve the Document and print it
        // System.out.println(client.get("my-first-document"));

        //System.out.println(client.get("21st_amendment_brewery_cafe-21a_ipa"));

        // 1: Get the View definition from the cluster
        View view = client.getView("beer", "brewery_beers");


// 2: Create the query object
        Query query = new Query();
        query.setIncludeDocs(true);
        query.setLimit(5);


// 3: Query the cluster with the view and query information
        ViewResponse result = client.query(view, query);

        for(ViewRow row : result) {
            String k = row.getId();
            System.out.println("Key: " + k);
            System.out.println(row.getDocument());
            //System.out.println("Document: " + client.get(k));
            // The full document (as String) is available through row.getDocument();
        }
        // Shutting down properly
        client.shutdown();


    }

}
