server {
    listen  80;
    server_name plant.dempseywood.nz;
    if ($http_x_forwarded_proto != "https") {
        rewrite ^(.*)$ https://$server_name$REQUEST_URI permanent;
    }
}
