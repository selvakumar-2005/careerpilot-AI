package com.careerpilot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Enables JPA Auditing so @CreatedDate and @LastModifiedDate
 * on the User entity are automatically populated.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    // No additional beans required — annotation does the work.
}
