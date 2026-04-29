/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author daya
 */
public class mongoConfig {
    
    private mongoConfig(){
        
    }
    static MongoClientSettings buildSettings(String url){
        ConnectionString conn = new ConnectionString(url);
        
        CodecRegistry pojoCodeRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        return MongoClientSettings.builder()
                .applyConnectionString(conn)
                .codecRegistry(pojoCodeRegistry)
                .build();
    }
}
