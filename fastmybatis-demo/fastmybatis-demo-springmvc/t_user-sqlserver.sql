CREATE TABLE [dbo].[t_user] (
  [id] int IDENTITY(1, 1) NOT NULL,
  [username] varchar(50) COLLATE Chinese_PRC_CI_AS NULL,
  [state] tinyint NULL,
  [isdel] bit NULL,
  [remark] varchar(255) COLLATE Chinese_PRC_CI_AS NULL,
  [add_time] datetime NULL,
  [money] decimal(10, 2) NULL,
  [left_money] float NULL,
  CONSTRAINT [PK__t_user__3213E83F7F60ED59] PRIMARY KEY CLUSTERED ([id])
)
ON [PRIMARY]
GO

CREATE TABLE [dbo].[user_info] (
  [id] int IDENTITY(1, 1) NOT NULL,
  [user_id] int NULL,
  [city] varchar(50) COLLATE Chinese_PRC_CI_AS NULL,
  [address] varchar(100) COLLATE Chinese_PRC_CI_AS NULL,
  [status] varchar(4) COLLATE Chinese_PRC_CI_AS NULL DEFAULT '0',
  [create_time] datetime NULL,
  CONSTRAINT [PK__user_inf__3213E83F0519C6AF] PRIMARY KEY CLUSTERED ([id])
)
ON [PRIMARY]
GO



/* Data for the `dbo.t_user` table  (Records 1 - 6) */

set identity_insert [dbo].[t_user] ON
GO

INSERT INTO [dbo].[t_user] ([id], [username], [state], [isdel], [remark], [add_time], [money], [left_money])
VALUES (3, N'王五', 0, 0, N'批量修改备注', '20170221 10:37:44', 101.1, 22.1)
GO

INSERT INTO [dbo].[t_user] ([id], [username], [state], [isdel], [remark], [add_time], [money], [left_money])
VALUES (4, N'张三', 0, 0, N'批量修改备注', '20170221 10:40:11', 100.5, 22.1)
GO

INSERT INTO [dbo].[t_user] ([id], [username], [state], [isdel], [remark], [add_time], [money], [left_money])
VALUES (5, N'张三', 1, 0, N'备注', '20170221 10:40:11', 100.5, 22.1)
GO

INSERT INTO [dbo].[t_user] ([id], [username], [state], [isdel], [remark], [add_time], [money], [left_money])
VALUES (6, N'张三', 0, 0, N'批量修改备注', '20170221 10:40:11', 100.5, 22.1)
GO

INSERT INTO [dbo].[t_user] ([id], [username], [state], [isdel], [remark], [add_time], [money], [left_money])
VALUES (7, N'张三', 0, 0, N'批量修改备注', '20170221 10:40:11', 100.5, 22.1)
GO

INSERT INTO [dbo].[t_user] ([id], [username], [state], [isdel], [remark], [add_time], [money], [left_money])
VALUES (8, N'张三', 0, 0, N'批量修改备注', '20170221 10:40:11', 100.5, 22.1)
GO

set identity_insert [dbo].[t_user] OFF
GO




/* Data for the `dbo.user_info` table  (Records 1 - 6) */

set identity_insert [dbo].[user_info] ON
GO

INSERT INTO [dbo].[user_info] ([id], [user_id], [city], [address], [create_time])
VALUES (1, 3, N'杭州', N'延安路', '20170822 10:27:56.150')
GO

INSERT INTO [dbo].[user_info] ([id], [user_id], [city], [address], [create_time])
VALUES (2, 4, N'杭州', N'延安路', '20170822 10:27:56.150')
GO

INSERT INTO [dbo].[user_info] ([id], [user_id], [city], [address], [create_time])
VALUES (3, 5, N'杭州', N'延安路', '20170822 10:27:56.150')
GO

INSERT INTO [dbo].[user_info] ([id], [user_id], [city], [address], [create_time])
VALUES (4, 6, N'杭州', N'延安路', '20170822 10:27:56.150')
GO

INSERT INTO [dbo].[user_info] ([id], [user_id], [city], [address], [create_time])
VALUES (5, 7, N'杭州', N'延安路', '20170822 10:27:56.150')
GO

INSERT INTO [dbo].[user_info] ([id], [user_id], [city], [address], [create_time])
VALUES (6, 8, N'杭州', N'延安路', '20170822 10:27:56.150')
GO

set identity_insert [dbo].[user_info] OFF
GO

CREATE TABLE [dbo].[address] (
  [id] varchar(100) NOT NULL,
  [address] varchar(200) NULL,
  PRIMARY KEY CLUSTERED ([id])
)
GO