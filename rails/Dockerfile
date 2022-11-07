# Use Ruby 3.1.2 version
FROM ruby:3.1.2

# Install needed dependencies
RUN apt-get update -qq && apt-get install -y build-essential nodejs  

# Throw errors if Gemfile has been modified since Gemfile.lock
RUN bundle config --global frozen 1

# Setup the Rails files
RUN mkdir /rails
ADD . /rails
WORKDIR /rails
RUN bundle install

# Expose the port so we can connect to it
EXPOSE 3000

# Run "bash" so it automatically goes to shell
CMD ["bash"]