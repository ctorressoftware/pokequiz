package com.ctorres.pokequiz.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name="refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="user_id")
    private  User user;
    
    @Column(name="token_hash", nullable=false, length=64)
    private  String tokenHash;
    
    @Column(name="issued_at", nullable=false)
    private OffsetDateTime issuedAt;
    
    @Column(name="expires_at", nullable=false)
    private OffsetDateTime expiresAt;
    
    @Column(name="revoked_at", nullable = true)
    private OffsetDateTime revokedAt;
    
    @Column(name="replaced_by_hash", length=64)
    private String replacedByHash;
    
    @Column(name="ip_address", length=45)
    private String ip;
    
    @Column(name="user_agent", length=255)
    private String userAgent;
  }
