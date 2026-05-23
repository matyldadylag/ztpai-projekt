# ZTPAI Projekt

Krótka aplikacja REST API w Spring Boot do zarządzania klientami, produktami i zamówieniami. Projekt wykorzystuje Spring Web, Spring Data JPA, Spring Security, JWT oraz PostgreSQL.

## Funkcje

- logowanie i autoryzacja JWT,
- CRUD dla klientów,
- CRUD dla produktów,
- CRUD dla zamówień klientów,
- walidacja danych wejściowych,
- globalna obsługa błędów,
- testy jednostkowe warstwy serwisów.

## Wymagania

- Java 25,
- Maven,
- PostgreSQL.

## Konfiguracja bazy danych

Domyślna konfiguracja znajduje się w pliku:

```properties
src/main/resources/application.properties
```

Domyślne dane połączenia:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
spring.datasource.username=myuser
spring.datasource.password=mypassword
```

Przed uruchomieniem utwórz bazę danych `mydatabase` albo zmień dane połączenia w `application.properties`.

## Uruchomienie projektu

W katalogu głównym projektu uruchom:

```bash
mvn spring-boot:run
```

Aplikacja będzie dostępna domyślnie pod adresem:

```text
http://localhost:8080
```

## Testy

Aby uruchomić testy:

```bash
mvn test
```

## Logowanie

Endpoint logowania:

```http
POST /api/auth/login
```

Przykładowe dane logowania:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

W odpowiedzi aplikacja zwraca token JWT. Przy kolejnych zapytaniach należy dodać nagłówek:

```http
Authorization: Bearer TOKEN
```

## Główne endpointy

```text
/api/customers
/api/products
/api/orders
```

Dla każdej z tych ścieżek dostępne są podstawowe operacje CRUD:

```http
POST   /api/...
GET    /api/...
GET    /api/.../{id}
PUT    /api/.../{id}
DELETE /api/.../{id}
```

## Struktura projektu

```text
controller  - kontrolery REST
dto         - klasy request/response
exception   - obsługa błędów API
mapper      - mapowanie DTO <-> encje
model       - encje JPA
repository  - repozytoria Spring Data JPA
security    - konfiguracja JWT i Spring Security
service     - logika biznesowa
```
