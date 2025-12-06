# ARG ELASTIC_VERSION

# https://www.docker.elastic.co/
FROM logstash:8.17.4

RUN logstash-plugin install logstash-output-loki

# Add your logstash plugins setup here
# Example: RUN logstash-plugin install logstash-filter-json
