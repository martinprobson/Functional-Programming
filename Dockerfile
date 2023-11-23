FROM ruby:2.7
RUN apt-get -y update && apt-get -y install libicu-dev cmake git && rm -rf /var/lib/apt/lists/*
RUN gem install github-linguist
RUN gem install gollum
RUN gem install org-ruby 
RUN git config --global --add safe.directory '*'
WORKDIR /wiki
ENTRYPOINT ["gollum","--port","80","--ref","main","--lenient-tag-lookup","--no-edit"]
EXPOSE 80
