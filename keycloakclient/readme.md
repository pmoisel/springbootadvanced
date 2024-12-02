# Ermöglicht einfache Anbindung eines Keycloaks

- inklusive Role-Mapping

```yaml
nterra:
  spring:
    security:
      oauth2:
        keycloakclient:
          client-secret: swYAnmMiWEdE1598Fe2ZC2nR99IDRWx7
          client-id: nterra
          uri: http://localhost:8180
          realm: schulung
          redirect-uri: http://localhost:8080
```