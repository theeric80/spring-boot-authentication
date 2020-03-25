package org.theeric.auth.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public abstract class AbstractService implements ApplicationEventPublisherAware {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    protected void publishEvent(ApplicationEvent event) {
        publisher.publishEvent(event);
    }

}
