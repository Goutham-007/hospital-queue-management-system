# Architecture

## Style
Modular monolith: one Spring Boot app, package-per-feature.
Each feature has controller, service, repository, entity and dto.

## Rules
1. Controller calls only its own service.
2. A service uses another module only through that module's service.
3. Modules communicate through events where possible.

## Design patterns (where they live)
| Pattern  | Package               | Purpose                          |
|----------|-----------------------|----------------------------------|
| Strategy | payment/strategy      | Pick payment method at runtime   |
| Factory  | notification/factory  | Create notification types        |
| Builder  | booking/builder       | Build booking objects step by step |
| Observer | queue/event           | Notify listeners on queue change |

## Request flow
React -> REST controller -> service -> repository -> MySQL
Live updates: service publishes event -> WebSocket -> React
Notification logs: MongoDB