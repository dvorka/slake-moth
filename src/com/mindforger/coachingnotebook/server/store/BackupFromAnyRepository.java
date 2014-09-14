package com.mindforger.coachingnotebook.server.store;


/*
 * Key map:
 * 
 *   User
 *     xxx
 *   UserSettings
 *     xxx
 *   LifeVision
 *     xxx
 *   Question
 *     xxx
 *   WhitelistEntry
 *     
 *   Grow
 *     > User
 *   
 *   Friend
 *     > User
 *   CheckListAnswer
 *     > Grow
 *   QuestionAnswer
 *     > Grow
 *      
 *   Attachment
 *     > Grow
 *   Comment
 *     > User
 *     > Grow
 *     > Question
 *        
 *   Permission
 *     > User
 *     > any resource
 */
public class BackupFromAnyRepository {
}
