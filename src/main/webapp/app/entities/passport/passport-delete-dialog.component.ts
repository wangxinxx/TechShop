import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPassport } from 'app/shared/model/passport.model';
import { PassportService } from './passport.service';

@Component({
    selector: 'jhi-passport-delete-dialog',
    templateUrl: './passport-delete-dialog.component.html'
})
export class PassportDeleteDialogComponent {
    passport: IPassport;

    constructor(protected passportService: PassportService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.passportService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'passportListModification',
                content: 'Deleted an passport'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-passport-delete-popup',
    template: ''
})
export class PassportDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ passport }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PassportDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.passport = passport;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/passport', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/passport', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
