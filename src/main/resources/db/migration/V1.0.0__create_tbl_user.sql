CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `role` enum('ROLE_ADMIN','ROLE_USER') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ROLE_USER',
  `username` varchar(128) COLLATE utf8mb4_persian_ci NOT NULL,
  `password` varchar(128) COLLATE utf8mb4_persian_ci NOT NULL,
  `firstname` varchar(128) COLLATE utf8mb4_persian_ci NOT NULL DEFAULT '',
  `lastname` varchar(128) COLLATE utf8mb4_persian_ci NOT NULL DEFAULT '',
  `email` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;