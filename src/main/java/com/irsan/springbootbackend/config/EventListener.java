//package com.irsan.springbootbackend.config;
//
//import lombok.RequiredArgsConstructor;
//import org.hibernate.event.service.spi.EventListenerRegistry;
//import org.hibernate.event.spi.EventType;
//import org.hibernate.event.spi.PreInsertEvent;
//import org.hibernate.event.spi.PreInsertEventListener;
//import org.hibernate.internal.SessionFactoryImpl;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManagerFactory;
//
///**
// * @author: Irsan Ramadhan
// * @email: irsan.ramadhan@iconpln.co.id
// */
//@Component
//@RequiredArgsConstructor
//public class EventListener implements PreInsertEventListener {
//
//    private final EntityManagerFactory entityManagerFactory;
//
//    @PostConstruct
//    private void init() {
//        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
//        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
//        registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(this);
//    }
//
//    @Override
//    public boolean onPreInsert(PreInsertEvent preInsertEvent) {
//        return false;
//    }
//}
