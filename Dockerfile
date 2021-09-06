FROM gradle
COPY . /app
WORKDIR /app
ENTRYPOINT chmod +x tester.sh
CMD ["./tester.sh"]

