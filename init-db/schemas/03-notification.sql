CREATE TABLE IF NOT EXISTS "NotificationLogs" (
    "Id" uuid PRIMARY KEY,

    "Type" varchar(50) NOT NULL,
    "Recipient" text NOT NULL,
    "Subject" text NOT NULL,

    "Metadata" jsonb,

    "Status" varchar(50) NOT NULL,

    "SentAt" timestamptz,
    "ErrorMessage" text,

    "RetryCount" integer NOT NULL DEFAULT 0,

    "CreatedAt" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,

    "ReferenceId" text NOT NULL
);

-- Indexes
CREATE INDEX IF NOT EXISTS "IX_NotificationLogs_Status"
    ON "NotificationLogs" ("Status");

CREATE INDEX IF NOT EXISTS "IX_NotificationLogs_Type"
    ON "NotificationLogs" ("Type");

CREATE INDEX IF NOT EXISTS "IX_NotificationLogs_CreatedAt"
    ON "NotificationLogs" ("CreatedAt");

-- Idempotency
CREATE UNIQUE INDEX IF NOT EXISTS "UX_NotificationLogs_ReferenceId"
    ON "NotificationLogs" ("ReferenceId");