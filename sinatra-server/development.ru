require 'rubygems'
require 'bundler/setup'
require 'sinatra'
require 'rack'
require File.dirname(__FILE__)+'/main'

set :environment, :development

set :port, 8091
set :server, 'webrick'

Sinatra::Application.run
