package workflow.domains.request;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import workflow.domains.user.User;
import workflow.domains.workflow.Workflow;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "requests")
public class Request {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * 申請タイトル
     */
    @Column(nullable = false)
    private String title;


    /**
     * 申請内容
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;


    /**
     * 申請者
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "requested_by",
            nullable = false
    )
    private User requestedBy;


    /**
     * 使用する承認フロー
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "workflow_id",
            nullable = false
    )
    private Workflow workflow;


    /**
     * 現在状態
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;


    /**
     * 作成日時
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;


    /**
     * 更新日時
     */
    private LocalDateTime updatedAt;



    public Request(
            String title,
            String content,
            User requestedBy,
            Workflow workflow
    ) {

        this.title = title;
        this.content = content;
        this.requestedBy = requestedBy;
        this.workflow = workflow;
        this.status = RequestStatus.SUBMITTED;
        this.createdAt = LocalDateTime.now();

    }



    /**
     * 状態変更
     */
    public void changeStatus(
            RequestStatus status
    ) {

        this.status = status;
        this.updatedAt = LocalDateTime.now();

    }

}