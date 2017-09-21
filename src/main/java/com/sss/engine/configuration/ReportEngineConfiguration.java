package com.sss.engine.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.management.Query;
import javax.xml.stream.XMLInputFactory;

import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.sss.engine.model.Profile;
import com.sss.engine.model.ProfileProperty;

@Component
public class ReportEngineConfiguration {
	
	@Value("${application.parent.model.canonical.class.name}")
	private String parentClassName;
	@Value("${application.model.package}")
	private String applicationModelPackage;
	
	private Comparator<Class<?>> classComparator = new Comparator<Class<?>>() {
		
		@Override
		public int compare(Class<?> o1, Class<?> o2) {
			// TODO Auto-generated method stub
			return o1.getSimpleName().compareTo(o2.getSimpleName());
		}
	};
	
	@Bean
	public List<Class<? extends ProfileProperty>> applicationModelClasses() {
		Reflections applicationModel = new Reflections(applicationModelPackage);
		Set<Class<? extends ProfileProperty>> applicationModelClassSet = applicationModel.getSubTypesOf(ProfileProperty.class);
		List<Class<? extends ProfileProperty>> applicationModelClassList = new ArrayList<>(applicationModelClassSet);
		Collections.sort(applicationModelClassList, classComparator );
		return applicationModelClassList;
	}

	@Bean
	public Map<String,Profile> profileCache() {
		return new ConcurrentSkipListMap<>();
	}
	
	@Bean/*({"xmlParser","processor","filteredReportsGenerator","supplementaryReportsGenerator"})*/
	//@Scope(scopeName = "prototype")
    public TaskExecutor taskExecutor() {
		int noOfProcessors = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("applicationThreadPool-");
        executor.initialize();
        executor.setCorePoolSize(noOfProcessors * 2);
        return executor;
    }
	
	@Bean
	public TaskExecutor applicationSubThreadPool() {
		int noOfProcessors = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("applicationSubThreadPool-");
        executor.initialize();
        executor.setCorePoolSize(noOfProcessors * 2);
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
	public Query query() {
		return  new Query();
	}
	
}
	