package rest;

import com.google.gson.Gson;
import entity.uuidEnt;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("randomuuid")
public class PersonResource
{
    @Context
    private UriInfo context;

    @Context
    HttpHeaders headers;

    public PersonResource()
    {
    }

    
    @GET
    public String generateUUID()
    {
        return "{\"uuid\":\"" +UUID.randomUUID().toString() + "\"}";
    }

    @GET
    @Path("{timestamp}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTimeStamp(@PathParam("timestamp") int arg)
    {

        if(arg == 1){
             return "{\"uuid\":\"" +UUID.randomUUID().toString() + "\",\"created\":\""+ new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss").format(new Date()) + "\"   ";
        }else if(arg == 0){
            return "{\"uuid\":\"" +UUID.randomUUID().toString() + "\"}";
        }
        return "{Invalid URL-Argument, use either 1 or 0}";
    }
    
    //Hurtig og effektivt Stringbuilder system, dog ikke særlig pæn kode
    //Den næste og mere avancerede opgave er løst med den "rigtige" og smarte måde
    @GET
    @Path("{timestamp}/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public String timeStampCount(@PathParam("timestamp") int arg, @PathParam("count") int arg2)
    {

        if(arg == 1){
            if(arg2 >= 1){
                
                StringBuilder builder = new StringBuilder();
                builder.append("{\n\"uuid\":["+"\n");
                for (int i = 0; i < arg2; i++)
                {
                    builder.append("\"");
                    builder.append(UUID.randomUUID().toString());
                    builder.append("\","+"\n");
                }
               builder.deleteCharAt(builder.length()-2);
                builder.append("],\n\"created\":\""+ new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss").format(new Date()) + "\"\n}   ");
          
                return builder.toString();
            }else{
            
            return "{\"uuid\":\"" +UUID.randomUUID().toString() + "\",\"created\":\""+ new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss").format(new Date()) + "\"   ";
            }
        }else if(arg == 0){
            return "{\"uuid\":\"" +UUID.randomUUID().toString() + "\"}";
        }
        return "{Invalid URL-Argument, use either 1 or 0 as the first argument, and the number of uuid's wanted as the second argument}";
    }


    
    //Smartere måde at gøre tingene, dette gør også klar til evt Database brug
    //via JPA, kunne sagtens også bruges ovenover, men jeg vil gerne vise
    //2 forskellige løsninger
    @POST
    @Path("{timestamp}/{count}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postJson(String content, @PathParam("timestamp") int arg, @PathParam("count") int arg2)
    {
        uuidEnt input = new Gson().fromJson(content, uuidEnt.class);
        
        if(arg == 1){
            if(arg2 >= 1){
                
                input.setCreated(new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss").format(new Date()));
                List<String> tempList = new ArrayList<String>();
                
                for (int i = 0; i < arg2; i++)
                {
                    tempList.add(UUID.randomUUID().toString());
                }
              
                input.setUuids(tempList);
                
            }else{
            
            input.setCreated(new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss").format(new Date()));
            }
        }else if(arg == 0){
            return "{\"uuid\":\"" +UUID.randomUUID().toString() + "\"}";
        }
        
       return new Gson().toJson(input);
    }
    
}
