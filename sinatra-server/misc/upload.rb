#!/usr/bin/env ruby
require 'rubygems'
require 'httpclient'

if ARGV.size < 1
  STDERR.puts 'ruby upload.rb /path/to/audio.mp3'
  exit 1
end

c = HTTPClient.new
res = nil

open(ARGV.first){|file|
  postdata = {
    'file' => file
  }
  puts res = c.post_content('http://localhost:8091/upload', postdata)
}
