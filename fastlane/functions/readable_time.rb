#!/usr/bin/env groovy
#
#   readable_time.rb
#   sImageViewer
#   Created by Duncan Wallace 12/16/2021
#   Copyright © 2021. Duncwa LLC.  All rights reserved

def to_clock(secs)
 Time.at(secs.to_i).utc.strftime("%H: hrs %M: mins %S secs. ")
end
