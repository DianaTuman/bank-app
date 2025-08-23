# Bank-App: Jenkins + Docker + Kubernetes + Helm

## Что делает проект

Проект автоматизирует CI/CD процесс для всех микросервисов приложения. Он выполняет:

- сборку и тестирование кода;
- сборку Docker-образов;
- публикацию образов в GitHub Container Registry (GHCR);
- деплой в кластер Kubernetes с помощью Helm.

Все этапы выполняются через Jenkins Pipeline.

## Состав проекта

- Jenkins (в Docker-контейнере)
- Kubernetes (включён в Docker Desktop)
- Helm и kubectl (предустановлены в контейнере Jenkins)
- Docker Registry: GHCR
- Helm-чарты для каждого сервиса
- CI/CD pipeline (`Jenkinsfile`)

---

## Подготовка

### 1. Установите зависимости

Для запуска проекта вам понадобятся:

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- Включённый Kubernetes в Docker Desktop (настройка → Kubernetes → Enable Kubernetes)
- Установленный [Git](https://git-scm.com/)

> Устанавливать Helm или kubectl локально не нужно — они уже установлены в Jenkins-контейнере.

---

### 2. Клонируйте проект и залейте его на свой GitHub

> Jenkins работает с удалённым репозиторием. Поэтому проект нужно сразу разместить в вашем аккаунте на GitHub.

Выполните в терминале:

```bash
git init
git remote add origin https://github.com/<your-username>/bank-app.git
git add .
git commit -m "Initial commit"
git push -u origin master
```

---

### 3. Обновите файл `config` в директории jenkins/kube

Jenkins будет использовать этот файл для доступа к Kubernetes.

Выполните в терминале:

```bash
cp ~/.kube/config jenkins_kubeconfig.yaml
```
Затем скопируйте из него client-certificate-data и client-key-data значения в файл `config` в директории jenkins/kube:

Это нужно, чтобы Jenkins внутри контейнера смог обратиться к вашему локальному кластеру и проигнорировал самоподписанные сертификаты.

---

### 4. Установите Ingress Controller в кластер

**Ingress Controller** — это компонент, который позволяет обращаться к сервисам Kubernetes через удобные HTTP-домены (например, `http://order.test.local`).

Мы используем `ingress-nginx`. Установите его в кластер:

```bash
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update

helm upgrade --install ingress-nginx ingress-nginx/ingress-nginx   --namespace ingress-nginx --create-namespace
```

---

### 5. Обновите `.env` файл

Обновите файл `.env` в директории jenkins своими значениями:

> Убедитесь, что ваш GitHub Token имеет права `write:packages`, `read:packages` и `repo`.

---

### 6. Запустите Jenkins

```bash
cd jenkins
docker compose up -d --build
```

Jenkins будет доступен по адресу: [http://localhost:8080](http://localhost:8080)

---

## Как использовать

1. Откройте Jenkins: [http://localhost:8080](http://localhost:8080)
2. Перейдите в задачу `Bank-App` → `main` → `Build Now`
3. Jenkins выполнит:
    - сборку и тесты
    - сборку Docker-образов

---

## Проверка успешного деплоя
### 1. Добавьте записи в `/etc/hosts`

```bash
sudo nano /etc/hosts
```

Добавьте:

```text
127.0.0.1 bank-ui.bank-app.local
127.0.0.1 zipkin.bank-app.local
127.0.0.1 grafana.bank-app.local
127.0.0.1 kibana.bank-app.local
```

### 2. Отправьте запросы на `/actuator/health`

```bash
curl -s http://bank-ui.bank-app.local/actuator/health
```

**Ожидаемый ответ:**

```json
{"status":"UP","groups":["liveness","readiness"]}
```

### 3. Можете открыть приложение в браузере

Приложение будет доступно по адресу: http://bank-ui.bank-app.local/
---

Zipkin: http://zipkin.bank-app.local
Grafana: http://grafana.bank-app.local
Kibana: http://kibana.bank-app.local

## Завершение работы и очистка

Если вы хотите полностью остановить Jenkins, удалить все установленные ресурсы, используйте скрипт `nuke-all.sh`.

Он находится в папке `jenkins`:

```bash
cd jenkins
./nuke-all.sh
```