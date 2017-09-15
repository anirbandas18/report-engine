package com.sss.engine.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;

import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;
import com.sss.engine.core.tags.ProfileField;

import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;

@Component
public class ReportEngineConfiguration {
	
	@Value("${application.parent.model.canonical.class.name}")
	private String parentClassName;
	@Value("${application.model.package}")
	private String applicationModelPackage;
	@Value("${application.mongo.db.url}")
	private String mongoDBUrl;
	@Value("${application.mongo.db.name}")
	private String mongoDBName;
	
	private Comparator<Class<?>> classComparator = new Comparator<Class<?>>() {
		
		@Override
		public int compare(Class<?> o1, Class<?> o2) {
			// TODO Auto-generated method stub
			return o1.getSimpleName().compareTo(o2.getSimpleName());
		}
	};
	
	@Bean
	public List<Class<?>> applicationModelClasses() {
		Reflections applicationModel = new Reflections(applicationModelPackage);
		Set<Class<?>> applicationModelClassSet = applicationModel.getTypesAnnotatedWith(ProfileField.class);
		List<Class<?>> applicationModelClassList = new ArrayList<>(applicationModelClassSet);
		Collections.sort(applicationModelClassList, classComparator );
		return applicationModelClassList;
	}

	@Bean
    public TaskExecutor xmlParsingThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("xmlParser-");
        executor.initialize();
        return executor;
    }
	
	@Bean
	public Class<?> parentModelClass() throws ClassNotFoundException {
		return Class.forName(parentClassName);
	}
	
	@Bean
	public XMLInputFactory xmlInputFactory() {
		 XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		 xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
		 return xmlInputFactory;
	}
	
	@Bean
    public MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(mongoDBUrl);
        MongoClient mongoClient = mongo.getObject();
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, mongoDBName);
        return mongoTemplate;
    }
	
}
