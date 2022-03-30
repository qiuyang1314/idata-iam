-- 机构
INSERT INTO `iam_office`(`id`, `parent_id`, `parent_ids`, `name`, `code`, `paths`, `order_index`, `app_id`, `district_id`, `otype`, `levels`, `address`, `supervisor`, `phone`, `fax`, `email`, `useable`, `del_flag`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES ('99721bce7d71bad8e1a48776ff4448c5', '-1', '', 'XX干部退休', '003', '003/', 1, NULL, '', '', '', '', '', '', '', '', '0', '1', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-05-31 20:10:05', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-05-31 20:10:14');
INSERT INTO `iam_office`(`id`, `parent_id`, `parent_ids`, `name`, `code`, `paths`, `order_index`, `app_id`, `district_id`, `otype`, `levels`, `address`, `supervisor`, `phone`, `fax`, `email`, `useable`, `del_flag`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES ('d25f613033c5e0834df3412b362f1a97', '-1', '', '党委会', '003', '003/', 1, NULL, '', '6', '1', '', '', '', '', '', '0', '0', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-05-31 20:11:07', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-06-01 16:26:52');
INSERT INTO `iam_office`(`id`, `parent_id`, `parent_ids`, `name`, `code`, `paths`, `order_index`, `app_id`, `district_id`, `otype`, `levels`, `address`, `supervisor`, `phone`, `fax`, `email`, `useable`, `del_flag`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES ('f01134e5dca84645099535ba9f75a048', '-1', '', 'XX党委会', '002', '002/', 1, NULL, '', '', '', '', '', '', '', '', '0', '0', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-05-31 20:19:53', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2021-05-31 20:19:53');

-- 用户
INSERT INTO `iam_user`(`id`, `name`, `sex`, `avatar`, `nation`, `birthday`, `id_card`, `phone`, `other_contact`, `secirity_flag`, `office_id`, `user_post`, `user_level`, `budgeted`, `budgeted_post`, `del_flag`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES ('c9b5ae11353c4ab766b3b2c1aa189860', '小林', '0', '', '', '', '', '', '', '1', '7c97cdfa4e4c561b9ca088cfcdbf51c3', '查税', '正厅级', '', '', '0', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-10-30 11:17:41', 'f900423f19e9eb6a7bd61cf8e6feb4f5', '2020-10-30 11:17:41');

-- demo用户账号（密码111222）
INSERT INTO `iam_user_code`(`id`, `user_id`, `login_name`, `passwd`, `salt`, `type`, `status`, `del_flag`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES ('f900423f19e9eb6a7bd61cf8e6feb4f5', 'c9b5ae11353c4ab766b3b2c1aa189860', 'caifan', '2@db2224d13#1b3a2g@fa31*#Fcb52c@bg!2!1!03446#6b042320*3ca6#f2#5!', '306238', '1', '0', '0', '81842a7795700362f79cafb962910169', '2020-10-26 10:24:53', '81842a7795700362f79cafb962910169', '2020-12-18 14:31:21');

-- 统一角色
INSERT INTO `iam_role`(`id`, `app_id`, `role_name`, `role_code`, `role_type`, `useable`, `del_flag`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES ('960b9048ed0fd3fb8fca65fdb3fee70d', 'be9deea4c7d2d163fb719b6b500f5766', '统一权限角色', '1004', '1', '0', '0', '81842a7795700362f79cafb962910169', '2020-10-26 10:26:15', '81842a7795700362f79cafb962910169', '2020-10-26 10:26:15');

-- 统一角色权限
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '01c8ae6a3d7c7204d398f377dbcf4d9e');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '051c4a9d4593ac57fd2bcb9d49c09152');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '123befcd0fb8c6372b89e38797fcfc58');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '138d25d94c08b214fe7d6b70f193053f');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '13fc664734941059cae6691b0ec83db4');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '1c1d099cc76f2645c7cbd5bd3ca155ca');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '1d421cff44ca50fe2a62c327612031ff');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '1e37436c3287d62cce5f6d7b122121d0');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '47f3ef31b383c1bd6bf65bc920fbab6f');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '4a05fb0d5250a621a2bd826297a9c8cc');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '4bf2e5c68b14b74be714bba01691457a');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '4d547b194b1727590d06fb3b23d02825');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '503c31c1b282caff7d38f2783083ce97');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '50f3fdeed88457406fd6495c1e4bbd5b');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '5140bbc89068cb41d0045efc1bfbad29');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '54087ce8a0f6741131836caec2ae127e');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '5fc47b78453bc21b090c437b62b0f22b');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '68e603f26d374084224e5ab0c16b2278');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '6915e27a2d2b49b5da80beb593272590');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '6c6f02d456b3a17e172ae8eaed1fe951');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '6f754e0c57cd4819f9418cc937046e78');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '6fea46959b1b21f08a0de544695e551c');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '701ac0eadff87ad28deae1b0d1ad7317');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '758c5d5a11795c13a0eeda0d93903525');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '88ac71fdaf7c4964ed91f2ae36f0a496');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '8f3dca6446c18638a016869c45fa992e');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '9259bb06d06ed5fec9c4aaa93e4d03cd');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', '94f1b62f604765d2f9236472cfac0f01');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'a57a94ae6dec29982e038e23ed0c799e');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'a5b0d3a4470096547a5a1d5e116ca869');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'a5e72c9400569542d4f7f34f8e7f80f8');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'aa21ee374e0b555fd71f293a4f059ac7');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'ad936e2b94a32c8bd2e4d355724b9cde');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'b2ebb5d08d8eff75df00ce47a4ac00c8');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'b91dc1f75e92eaee2c044ac6209ab3c5');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'bb85b74922d3a4921df5110741d2b1b4');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'bd363e0ca1fccc5c1b89e3290d1311f2');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'bd5821da7d5e22cd651d841b101cc7e8');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'bdbeb03ad4d060d0485fe93df3ddafc6');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'be86c51e0ee2a855f6fded314dbd6e75');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'c3dc0fd08a6b2a5e17f2222d64385144');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'ca851b344c16d2df48f0ddfed452750e');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'cb8c8cd1635129f30a0a9b4299d8b581');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'cd01d2c621bd4a39afd932531d2ca01d');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'cdb620fb13c3ffc4192b3662c07f1f98');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'd0938c8e3e7093ea67dc9cc4640e6736');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'd0ce64724f06de734e27dcc1cf29667a');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'db889efc3159fa6338be745ce67aa2d7');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'df3770cfe25a6f5113834f05633b0f42');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'e1b615af87b6014f01bccd476c6d5147');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'e8fb8615f88210b45490e632cc80091d');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'e904121f686a887d1e0da8a0ea801d44');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'f094a744fc551a32ddc507d07d60c6b4');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'f33cf3a3127b1ed16c89af1bd8dfe447');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'f3ae6a6957feb2296f2e3604e2a4eeb4');
INSERT INTO `iam_role_menu`(`app_id`, `role_id`, `menu_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'f9cdd883c7b92d171e82fda03ced1e7b');

-- 账号关联系统权限
INSERT INTO `iam_role_user_code`(`app_id`, `role_id`, `user_code_id`) VALUES ('be9deea4c7d2d163fb719b6b500f5766', '960b9048ed0fd3fb8fca65fdb3fee70d', 'f900423f19e9eb6a7bd61cf8e6feb4f5');


/**
整理时间：2021-06-01
整理人：蔡凡
 */
