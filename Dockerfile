FROM gradle:jdk17-jammy

WORKDIR /app
COPY . .

CMD gradle bootRun