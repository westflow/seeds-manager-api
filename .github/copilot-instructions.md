# GitHub Copilot Instructions

## Commit Messages

Always generate commit messages following the **Conventional Commits** specification.

### Mandatory rules

1. The **commit type** must always be written in **English** and **lowercase**, using one of the following:

   * feat
   * fix
   * refactor
   * chore
   * test
   * docs
   * perf
   * build
   * ci

2. The **commit description** (after the colon) must be written in **English**, concise and meaningful.

3. The **commit body** must be written in **English**, using complete and explanatory sentences.

4. Do **not** use a period at the end of the commit title.

5. Use clear, objective, and technical language, appropriate for **Java Spring Boot** projects.

6. Prefer describing:

   * the business or technical intent
   * architectural or behavioral changes
   * relevant impacts (API, database, contracts, validations)

---

### Expected format

```text
<type>: <short description in English>

<Detailed description in English explaining:
- what was changed
- why it was changed
- relevant impacts or side effects (if any)>
```

---

### Examples

```text
feat: add TipoLegado support to order and delivery flows

Introduce optional TipoLegado parameter across commands, handlers,
validators, and service interfaces for orders and deliveries.
Update related models and REST controllers to propagate TipoLegado
when applicable. Adjust tests and infrastructure to ensure proper
validation and query string construction.
```

```text
fix: handle nullable total value in product response

Ensure null-safe handling of total values returned by the API.
Default missing values to zero to prevent deserialization errors
and runtime failures in downstream processing.
```
