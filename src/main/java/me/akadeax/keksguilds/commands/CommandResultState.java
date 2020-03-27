package me.akadeax.keksguilds.commands;

public enum CommandResultState {
    Success,
    // errors
    GuildAlreadyExists,
    GuildNotExists,
    AlreadyInGuild,
    NotInGuild,
    TargetInGuild,
    PlayerNotFound,
    IsLeader,
    NotLeader,
    NotInvited,
    InvitedSelf,
    MaxMembersReached
}
